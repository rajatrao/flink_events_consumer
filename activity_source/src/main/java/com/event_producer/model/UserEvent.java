package com.event_producer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

// User event structure
public class UserEvent {
    @JsonProperty("user_id")
    public String userId;

    @JsonProperty("event_type")
    public String eventType;

    @JsonProperty("event_time")
    public long eventTime;

    @JsonProperty("event_id")
    public String eventId;

    @JsonProperty("metadata")
    public Map<String, Object> metadata;

    public UserEvent() {}

    public UserEvent(String userId, String eventType, long eventTime, String eventId, Map<String, Object> metadata) {
        this.userId = userId;
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.eventId = eventId;
        this.metadata = metadata;
    }
}

