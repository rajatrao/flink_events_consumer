package com.event_streaming.mapper;


import com.event_streaming.model.UserEvent;
import com.event_streaming.model.UserEventCassandraModel;
import org.apache.flink.api.common.functions.MapFunction;

// Mapper  
public class UserEventMapper implements MapFunction<UserEvent, UserEventCassandraModel> {

    @Override
    public UserEventCassandraModel map(UserEvent event) {
        String bucketDate = event.getEventTimeAsDate();
        return new UserEventCassandraModel(
                event.getUser_id().toString(),
                bucketDate,
                event.getEvent_time().toEpochMilli(),
                event.getEvent_type(),
                event.getEvent_id().toString(),
                event.getMetadata().toString()
        );
    }
}
