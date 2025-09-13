package com.event_streaming;


import com.event_streaming.mapper.UserEventMapper;
import com.event_streaming.model.UserEvent;
import com.event_streaming.model.UserEventCassandraModel;
import com.event_streaming.sink.CassandraSinkFactory;
import com.event_streaming.source.KafkaSourceFactory;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.connector.kafka.source.KafkaSource;

public class Main {

    public static void main(String[] args) throws Exception {

        // Flink environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // Kafka Source
        KafkaSource<UserEvent> kafkaSource = KafkaSourceFactory.createKafkaSource();

        // Read and assign timestamps/watermarks
        DataStream<UserEvent> events = env.fromSource(kafkaSource, WatermarkAssigner.getWatermarkStrategy(),
                "KafkaSource");

        // Transform
        DataStream<UserEventCassandraModel> cassandraStream = events.map(new UserEventMapper());

        // Cassandra sink
        CassandraSinkFactory.addSink(cassandraStream);

        env.execute("User Events Streaming Pipeline");
    }
}
