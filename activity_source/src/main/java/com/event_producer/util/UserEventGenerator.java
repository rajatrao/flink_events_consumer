package com.event_producer.util;

import java.util.*;
import com.event_producer.model.UserEvent;

// Generates user event message from event_types 
// for a random user from given set
public class UserEventGenerator {

    private static final List<String> USER_IDS = Arrays.asList(
            "589e4567-e89b-12d3-a456-426614174000",
            "589e4567-e89b-12d3-a456-426614174001",
            "589e4567-e89b-12d3-a456-426614174002",
            "589e4567-e89b-12d3-a456-426614174003",
            "589e4567-e89b-12d3-a456-426614174004"
    );

    private static final List<String> EVENT_TYPES = Arrays.asList(
            "LOGIN", "LOGOUT", "PURCHASE", "VIEW_ITEM", "SIGNUP"
    );

    private static final Random RANDOM = new Random();

    public static UserEvent generate() {
        String userId = USER_IDS.get(RANDOM.nextInt(USER_IDS.size()));
        String eventType = EVENT_TYPES.get(RANDOM.nextInt(EVENT_TYPES.size()));
        long eventTime = System.currentTimeMillis();
        String eventId = UUID.randomUUID().toString();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("source", "web");
        metadata.put("session_id", UUID.randomUUID().toString());

        return new UserEvent(userId, eventType, eventTime, eventId, metadata);
    }
}


