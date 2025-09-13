package com.event_streaming;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;

import com.event_streaming.model.UserEvent;

import java.time.Duration;

// Support to handle late events
public class WatermarkAssigner {
    public static WatermarkStrategy<UserEvent> getWatermarkStrategy() {
        return WatermarkStrategy
                .<UserEvent>forBoundedOutOfOrderness(Duration.ofMinutes(2))
                .withTimestampAssigner((event, ts) -> event.getEvent_time().toEpochMilli());
    }
}
