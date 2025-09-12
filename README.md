# Flink user events consumer
Flink streaming job to consume and store user events

# Motivation
Users do many things on the website, like clicking, viewing pages, and submitting forms. 
We need an easy way to capture these actions at scale so we can understand user behavior, improve the experience, and grow the business.  

# Data flow

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

## Kafka events avro schema
```
{
  "type": "record",
  "name": "UserEvent",
  "namespace": "com.flink_streaming.events",
  "fields": [
    {
      "name": "user_id",
      "type": {
        "type": "string",
        "logicalType": "uuid"
      }
    },
    {
      "name": "event_type",
      "type": "string"
    },
    {
      "name": "event_time",
      "type": {
        "type": "long",
        "logicalType": "timestamp-millis"
      }
    },
    {
      "name": "event_id",
      "type": {
        "type": "string",
        "logicalType": "uuid"
      }
    },
    {
      "name": "metadata",
      "type": {
        "type": "map",
        "values": "string"
      },
      "default": {}
    }
  ]
}
```

## Usage details - Coming soon
