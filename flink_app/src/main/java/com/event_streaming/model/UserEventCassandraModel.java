package com.event_streaming.model;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;

@Table(keyspace = "event_streaming", name = "user_events")
public class UserEventCassandraModel {
    public UUID user_id;
    public String bucket_date;
    public long event_time;
    public String event_type;
    public UUID event_id;
    public String metadata;

    public UserEventCassandraModel() {
    }

    public UserEventCassandraModel(String user_id, String bucket_date, long event_time,
                                   String event_type, String event_id, String metadata) {
        this.user_id = UUID.fromString(user_id);
        this.bucket_date = bucket_date;
        this.event_time = event_time;
        this.event_type = event_type;
        this.event_id = UUID.fromString(event_id);
        this.metadata = metadata;
    }
}
