package com.event_streaming.sink;

import com.event_streaming.config.AppConfig;
import com.event_streaming.model.UserEventCassandraModel;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;

// Cassandra Sink Factory 
public class CassandraSinkFactory {

    public static void addSink(DataStream<UserEventCassandraModel> stream) throws Exception {
        String cassandraHost = AppConfig.get("cassandra.host");

        CassandraSink.addSink(stream)
                .setHost(cassandraHost)
                .build();
    }
}
