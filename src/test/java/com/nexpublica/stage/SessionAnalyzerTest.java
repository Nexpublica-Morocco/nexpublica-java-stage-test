package com.nexpublica.stage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("SessionAnalyzer")
class SessionAnalyzerTest {

    private final SessionAnalyzer analyzer = new SessionAnalyzer();

    private static Event e(String user, String action, String isoInstant) {
        return new Event(user, action, Instant.parse(isoInstant));
    }

    @Nested
    @DisplayName("Basics")
    class Basics {

        @Test
        @DisplayName("single user: login → logout = 30 minutes")
        void singleSession() {
            List<Event> events = List.of(
                    e("Alice", "login", "2026-03-10T10:00:00Z"),
                    e("Alice", "logout", "2026-03-10T10:30:00Z"));
            Map<String, Long> r = analyzer.totalSessionSecondsPerUser(events);
            assertEquals(30L * 60, r.get("Alice"));
        }

        @Test
        @DisplayName("action case-insensitive")
        void caseInsensitive() {
            List<Event> events = List.of(
                    e("Bob", "LOGIN", "2026-01-01T00:00:00Z"),
                    e("Bob", "LogOut", "2026-01-01T01:00:00Z"));
            assertEquals(3600L, analyzer.totalSessionSecondsPerUser(events).get("Bob"));
        }

        @Test
        @DisplayName("two independent users")
        void twoUsers() {
            List<Event> events = List.of(
                    e("A", "login", "2026-01-01T10:00:00Z"),
                    e("B", "login", "2026-01-01T10:15:00Z"),
                    e("A", "logout", "2026-01-01T11:00:00Z"),
                    e("B", "logout", "2026-01-01T11:30:00Z"));
            Map<String, Long> r = analyzer.totalSessionSecondsPerUser(events);
            assertEquals(3600L, r.get("A"));
            assertEquals(4500L, r.get("B"));
        }
    }

    @Nested
    @DisplayName("Ordering")
    class Ordering {

        @Test
        @DisplayName("events not sorted by time — must still be correct")
        void unsortedInput() {
            List<Event> events = List.of(
                    e("X", "logout", "2026-01-01T12:00:00Z"),
                    e("X", "login", "2026-01-01T10:00:00Z"));
            assertEquals(7200L, analyzer.totalSessionSecondsPerUser(events).get("X"));
        }

        @Test
        @DisplayName("same timestamp: preserve input order for tie-break")
        void stableTieBreak() {
            List<Event> events = new ArrayList<>();
            events.add(e("Y", "logout", "2026-01-01T10:00:00Z"));
            events.add(e("Y", "login", "2026-01-01T10:00:00Z"));
            Map<String, Long> r = analyzer.totalSessionSecondsPerUser(events);
            assertTrue(r.isEmpty() || !r.containsKey("Y") || r.get("Y") == 0L);
        }
    }

    @Nested
    @DisplayName("Edge cases")
    class EdgeCases {

        @Test
        @DisplayName("logout without login is ignored")
        void orphanLogout() {
            List<Event> events = List.of(e("Z", "logout", "2026-01-01T10:00:00Z"));
            assertTrue(analyzer.totalSessionSecondsPerUser(events).isEmpty());
        }

        @Test
        @DisplayName("double logout: second ignored")
        void doubleLogout() {
            List<Event> events = List.of(
                    e("Z", "login", "2026-01-01T10:00:00Z"),
                    e("Z", "logout", "2026-01-01T11:00:00Z"),
                    e("Z", "logout", "2026-01-01T12:00:00Z"));
            assertEquals(3600L, analyzer.totalSessionSecondsPerUser(events).get("Z"));
        }

        @Test
        @DisplayName("double login: second ignored")
        void doubleLogin() {
            List<Event> events = List.of(
                    e("Z", "login", "2026-01-01T10:00:00Z"),
                    e("Z", "login", "2026-01-01T10:30:00Z"),
                    e("Z", "logout", "2026-01-01T11:00:00Z"));
            assertEquals(3600L, analyzer.totalSessionSecondsPerUser(events).get("Z"));
        }

        @Test
        @DisplayName("session still open at end → close at global last event instant")
        void openUntilGlobalLast() {
            List<Event> events = List.of(
                    e("A", "login", "2026-01-01T08:00:00Z"),
                    e("B", "login", "2026-01-01T09:00:00Z"),
                    e("B", "logout", "2026-01-01T10:00:00Z"));
            Map<String, Long> r = analyzer.totalSessionSecondsPerUser(events);
            assertEquals(3600L, r.get("B"));
            assertEquals(2L * 3600, r.get("A"));
        }

        @Test
        @DisplayName("unknown action ignored")
        void unknownAction() {
            List<Event> events = List.of(
                    e("A", "login", "2026-01-01T10:00:00Z"),
                    e("A", "heartbeat", "2026-01-01T10:30:00Z"),
                    e("A", "logout", "2026-01-01T11:00:00Z"));
            assertEquals(3600L, analyzer.totalSessionSecondsPerUser(events).get("A"));
        }

        @Test
        @DisplayName("empty input")
        void empty() {
            assertTrue(analyzer.totalSessionSecondsPerUser(List.of()).isEmpty());
        }

        @Test
        @DisplayName("null list rejected")
        void nullList() {
            assertThrows(NullPointerException.class, () -> analyzer.totalSessionSecondsPerUser(null));
        }
    }

    @Nested
    @DisplayName("Sub-second truncation")
    class SubSecond {

        @Test
        @DisplayName("duration with millis truncates toward zero for seconds")
        void truncates() {
            List<Event> events = List.of(
                    e("A", "login", "2026-01-01T10:00:00Z"),
                    e("A", "logout", "2026-01-01T10:00:00.900Z"));
            Long v = analyzer.totalSessionSecondsPerUser(events).get("A");
            assertEquals(0L, v == null ? 0L : v);
        }
    }
}
