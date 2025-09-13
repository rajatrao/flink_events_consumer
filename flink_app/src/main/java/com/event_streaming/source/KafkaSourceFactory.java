package com.event_streaming.source;

import com.event_streaming.config.AppConfig;
import com.event_streaming.deserializer.JsonToUserEventDeserializationSchema;
import com.event_streaming.model.UserEvent;

import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

// Kafka source factory
public class KafkaSourceFactory {

    public static KafkaSource<UserEvent> createKafkaSource() {
        // configs
        String bootstrapServers = AppConfig.get("kafka.bootstrap.servers");
        String topic = AppConfig.get("kafka.topic");
        String groupId = AppConfig.get("kafka.group.id");

        return KafkaSource.<UserEvent>builder()
                .setBootstrapServers(bootstrapServers)
                .setTopics(topic)
                .setGroupId(groupId)
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new JsonToUserEventDeserializationSchema())
                .build();
    }
}
