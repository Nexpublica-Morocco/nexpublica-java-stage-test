package com.nexpublica.stage;

import java.time.Instant;
import java.util.Objects;

/**
 * Immutable application event from a product analytics stream.
 */
public final class Event {

    private final String user;
    private final String action;
    private final Instant timestamp;

    public Event(String user, String action, Instant timestamp) {
        this.user = Objects.requireNonNull(user, "user");
        this.action = Objects.requireNonNull(action, "action");
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp");
    }

    public String user() {
        return user;
    }

    /** Normalised to upper-case for comparison: {@code LOGIN}, {@code LOGOUT}. */
    public String action() {
        return action;
    }

    public Instant timestamp() {
        return timestamp;
    }
}
