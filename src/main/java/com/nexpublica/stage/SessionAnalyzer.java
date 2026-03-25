package com.nexpublica.stage;

import java.util.List;
import java.util.Map;

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
        throw new UnsupportedOperationException(
                "TODO: implement — run mvn test until all tests pass");
    }
}
