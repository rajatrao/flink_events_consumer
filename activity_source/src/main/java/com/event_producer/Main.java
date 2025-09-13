package com.event_producer;

import com.event_producer.config.AppConfig;
import com.event_producer.model.UserEvent;
import com.event_producer.producer.KafkaUserEventProducer;
import com.event_producer.util.UserEventGenerator;

// Produces user event to kafka every 5 secs
public class Main {
    public static void main(String[] args) {
        // Load config
        AppConfig config = new AppConfig("application.properties");
        String bootstrapServers = config.getBootstrapServers();
        String topic = config.getTopicName();
        System.out.println("config"+bootstrapServers + " , " + topic);
        KafkaUserEventProducer eventProducer = new KafkaUserEventProducer(bootstrapServers, topic);

        Runtime.getRuntime().addShutdownHook(new Thread(eventProducer::close));

        while (true) {
            UserEvent event = UserEventGenerator.generate();
            eventProducer.send(event);

            try {
                Thread.sleep(5000); // 5 seconds interval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}