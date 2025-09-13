package com.event_producer.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Application config
public class AppConfig {
    private final Properties properties;

    public AppConfig(String fileName) {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Unable to find config file: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config: " + e.getMessage(), e);
        }
    }

    // provides kafka bootstrap servers
    public String getBootstrapServers() {
        return properties.getProperty("bootstrap.servers");
    }

    // provides kafka topic
    public String getTopicName() {
        return properties.getProperty("topic.name");
    }
}
