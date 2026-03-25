package com.nexpublica.stage;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * <h2>Specification — total logged-in time per user</h2>
 *
 * <p>Events come from a web analytics stream: {@code user}, {@code action} ({@code login} or
 * {@code logout}, case-insensitive), {@code timestamp} (ISO-8601 instant).</p>
 *
 * <h3>Order</h3>
 * <ol>
 *   <li>Sort by {@code timestamp} ascending; stable tie-break using original list index.</li>
 *   <li>Process in that order.</li>
 * </ol>
 *
 * <h3>Per-user rules</h3>
 * <ul>
 *   <li><b>LOGIN</b> while logged out → open session at this timestamp.</li>
 *   <li><b>LOGIN</b> while already logged in → ignore.</li>
 *   <li><b>LOGOUT</b> while logged in → add {@code Duration.between(login, logout)} in seconds
 *       (truncate sub-second remainder toward zero) to total; then logged out.</li>
 *   <li><b>LOGOUT</b> while logged out → ignore.</li>
 *   <li>Unknown actions → ignore.</li>
 *   <li>After all events, still logged in → close at the <b>max timestamp among all events
 *       in the feed</b> and add that duration.</li>
 * </ul>
 *
 * <h3>Output</h3>
 * Map user → total seconds; users with 0 may be omitted.
 */
public final class SessionAnalyzer {

    public Map<String, Long> totalSessionSecondsPerUser(List<Event> events) {
        Objects.requireNonNull(events, "events");
        if (events.isEmpty()) {
            return Map.of();
        }

        Instant globalLast = events.stream()
                .map(Event::timestamp)
                .max(Instant::compareTo)
                .orElseThrow();

        record Indexed(int index, Event event) {}
        List<Indexed> sorted = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            sorted.add(new Indexed(i, events.get(i)));
        }
        sorted.sort(Comparator.comparing((Indexed x) -> x.event.timestamp())
                .thenComparingInt(Indexed::index));

        Map<String, Long> totals = new HashMap<>();
        Map<String, Instant> openSince = new HashMap<>();

        for (Indexed idx : sorted) {
            Event e = idx.event;
            String user = e.user();
            String action = e.action().trim().toUpperCase(Locale.ROOT);

            switch (action) {
                case "LOGIN" -> {
                    if (!openSince.containsKey(user)) {
                        openSince.put(user, e.timestamp());
                    }
                }
                case "LOGOUT" -> {
                    Instant start = openSince.remove(user);
                    if (start != null) {
                        addSeconds(totals, user, Duration.between(start, e.timestamp()));
                    }
                }
                default -> { /* ignore */ }
            }
        }

        for (Map.Entry<String, Instant> entry : openSince.entrySet()) {
            addSeconds(totals, entry.getKey(), Duration.between(entry.getValue(), globalLast));
        }

        totals.entrySet().removeIf(e -> e.getValue() == 0L);
        return totals;
    }

    private static void addSeconds(Map<String, Long> totals, String user, Duration d) {
        totals.merge(user, d.getSeconds(), Long::sum);
    }
}
