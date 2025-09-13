# 🌐 Real-Time User Event Streaming System

## Motivation
Users do many things on the website, like clicking, viewing pages, and submitting forms. 
We need an easy way to capture these actions at scale so we can understand user behavior, improve the experience, and grow the business.  

## Data flow

[Kafka Source] → [Parse + Transform] → [Extract bucket_date] → [Sink to Cassandra]

<img width="563" height="416" alt="Screenshot 2025-09-12 at 11 32 46 AM" src="https://github.com/user-attachments/assets/d34f8e49-1161-4949-a4d8-523badecec59" />

### Watermark support to handle late events
```
WatermarkStrategy<UserEvent> watermarkStrategy = WatermarkStrategy
    .<UserEvent>forBoundedOutOfOrderness(Duration.ofMinutes(2)) // allows 2 min lateness
    .withTimestampAssigner((event, timestamp) -> event.event_time.toEpochMilli());

```

## Events datastore schema

Bucket user events data by date
- Partition key: user_id + bucket_date
  - we can bucket by hour as well, bucketing is mainly done to avoid hotspotting of the partition
  
- Sort (clustering) key: event_time + event_type
  - added to sort the data within the partition

```
CREATE TABLE user_events (
    user_id UUID,
    bucket_date DATE,
    event_time TIMESTAMP,
    event_type TEXT,
    event_id UUID,
    metadata MAP<TEXT, TEXT>,
    PRIMARY KEY ((user_id, bucket_date), event_time, event_type)
) WITH CLUSTERING ORDER BY (event_time DESC, event_type ASC);
```

## Usage details 

This project is a full-stack, real-time event streaming pipeline built using:

- 🔄 **Kafka Producer** (Java) to simulate user events
- 🚀 **Apache Flink** to consume, transform, and route events
- 🧬 **Cassandra** to persist the transformed data

It enables end-to-end event stream processing, from **generation** ➡ **transformation** ➡ **storage**.

## 🔁 Data Flow

```text
UserEventProducer (Java) → Kafka → Flink → Cassandra
```
---

## ⚙️ Technologies Used

  - Java 11+

  - Apache Kafka

  - Apache Flink (v1.18.1)

  -  Apache Cassandra (v4.1)

  - Kafka UI for monitoring topics

  - Docker Compose for local orchestration

## Getting Started
  - Flink Streaming Consumer app

    ```text
      checkout flink_app/README.md
    ```

  - Java Kafka Producer app

    ```text
      checkout activity_source/README.md
    ```
