package com.event_streaming.deserializer;

import com.event_streaming.model.UserEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

// JSON deserializer for user event messages
public class JsonToUserEventDeserializationSchema implements DeserializationSchema<UserEvent> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
   
    @Override
    public UserEvent deserialize(byte[] message) throws IOException {
        try{
            objectMapper.registerModule(new JavaTimeModule());
            // Skip null or empty payloads
            if (message == null || message.length == 0) {
                System.err.println("**** Empty JSON skipped ****");
                return null;
            }
            return objectMapper.readValue(message, UserEvent.class);
        } catch (Exception e) {
            // Log and skip invalid messages
            System.err.println("**** Invalid JSON skipped: " + new String(message));
            return null;
        }
       
    }

    @Override
    public boolean isEndOfStream(UserEvent nextElement) {
        return false;
    }

    @Override
    public TypeInformation<UserEvent> getProducedType() {
        return TypeInformation.of(UserEvent.class);
    }
}
