package com.event_streaming.model;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.time.ZoneId;

// User Event structure
public class UserEvent {

    private UUID user_id;
    private String event_type;
    private Instant event_time;
    private UUID event_id;
    private Map<String, String> metadata;

    // Default constructor (required for Jackson)
    public UserEvent() {
    }

    // Getters and setters

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public Instant getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Instant event_time) {
        this.event_time = event_time;
    }

    public UUID getEvent_id() {
        return event_id;
    }

    public void setEvent_id(UUID event_id) {
        this.event_id = event_id;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getEventTimeAsDate() { 
        Instant instant = this.getEvent_time(); 
        return instant.atZone(ZoneId.of("UTC")).toLocalDate().toString(); // "2025-09-12"
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "user_id=" + user_id +
                ", event_type='" + event_type + '\'' +
                ", event_time=" + event_time +
                ", event_id=" + event_id +
                ", metadata=" + metadata +
                '}';
    }
}
