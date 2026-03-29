package com.nexpublica.stage;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

    private record IndexedEvent(int index, Event event) {}

    public Map<String, Long> totalSessionSecondsPerUser(List<Event> events) {
        Objects.requireNonNull(events, "events");
        if (events.isEmpty()) {
            return Map.of();
        }

        Instant globalMaxTs = events.stream()
                .map(Event::timestamp)
                .max(Comparator.naturalOrder())
                .orElseThrow();

        List<IndexedEvent> ordered = new ArrayList<>(events.size());
        for (int i = 0; i < events.size(); i++) {
            ordered.add(new IndexedEvent(i, events.get(i)));
        }
        ordered.sort(Comparator
                .comparing((IndexedEvent ie) -> ie.event().timestamp())
                .thenComparingInt(IndexedEvent::index));

        Map<String, Long> totals = new HashMap<>();
        Map<String, Instant> sessionStartIfLoggedIn = new HashMap<>();

        for (IndexedEvent ie : ordered) {
            Event ev = ie.event();
            String user = ev.user();
            String raw = ev.action();
            boolean login = "login".equalsIgnoreCase(raw);
            boolean logout = "logout".equalsIgnoreCase(raw);
            if (!login && !logout) {
                continue;
            }

            Instant ts = ev.timestamp();
            Instant openSince = sessionStartIfLoggedIn.get(user);

            if (login) {
                if (openSince == null) {
                    sessionStartIfLoggedIn.put(user, ts);
                }
            } else {
                if (openSince != null) {
                    long secs = Duration.between(openSince, ts).getSeconds();
                    totals.merge(user, secs, Long::sum);
                    sessionStartIfLoggedIn.remove(user);
                }
            }
        }

        for (Map.Entry<String, Instant> open : new ArrayList<>(sessionStartIfLoggedIn.entrySet())) {
            String user = open.getKey();
            Instant from = open.getValue();
            long secs = Duration.between(from, globalMaxTs).getSeconds();
            totals.merge(user, secs, Long::sum);
        }

        totals.entrySet().removeIf(e -> e.getValue() == 0L);
        return totals;
    }
}
