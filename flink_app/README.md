# 🚀 User Event Streaming Pipeline (Flink + Kafka + Cassandra)

This project is a real-time **streaming data pipeline** built with **Apache Flink**. It consumes user events from **Kafka**, transforms them, and stores the results in **Cassandra**.

---

## 🧱 Tech Stack

- [Apache Flink](https://flink.apache.org/) — stream processing engine
- [Apache Kafka](https://kafka.apache.org/) — message broker (source)
- [Apache Cassandra](https://cassandra.apache.org/) — NoSQL database (sink)
- Java 11+
- Maven

---

## 🗺️ Architecture Overview

- Kafka stores raw `UserEvent` JSON events.
- Flink reads from Kafka, maps them to `UserEventCassandraModel`.
- Transformed data is written into Cassandra.

---

## ⚙️ Prerequisites

- Java 11+
- Maven
- Kafka running with topic `user-events`
- Cassandra running with table to match `UserEventCassandraModel`
- Flink runtime (or use Flink CLI / IDE to run locally)

---

## 🛠️ Configuration

Make sure the following are properly configured:

### ✅ Kafka

- Bootstrap server set in `KafkaSourceFactory`
- Kafka topic: `user-events`
- Messages serialized as JSON matching `UserEvent.java`

### ✅ Cassandra

- Host, port, keyspace, and table configured in `CassandraSinkFactory`
- Table schema matches fields in `UserEventCassandraModel`

    #### Events datastore schema
    
        - Bucket user events data by date
        - Partition key: user_id + bucket_date
          - we can bucket by hour as well, bucketing is mainly done to avoid hotspotting of the partition
          
        - Sort (clustering) key: event_time + event_type
          - added to sort the data within the partition
        
        CREATE TABLE user_events (
            user_id UUID,
            bucket_date DATE,
            event_time TIMESTAMP,
            event_type TEXT,
            event_id UUID,
            metadata MAP<TEXT, TEXT>,
            PRIMARY KEY ((user_id, bucket_date), event_time, event_type)
        ) WITH CLUSTERING ORDER BY (event_time DESC, event_type ASC);
        

---

## ▶️ Getting Started

1. Start services
To simplify the setup, you can run Kafka, Cassandra, Flink, and supporting services using Docker Compose.

    ```bash
    cd flink_app
    docker compose up -d
    ```

2. Access Kafka UI

    Open: http://localhost:8080

3. Verify Kafka is ready

    List topics using Kafka UI or CLI:

    ```bash
    docker exec <kafka-container> kafka-topics --list --bootstrap-server kafka:29092
    ```
4. Create topic manually

    ```bash
    docker run --rm --network flink_app_default confluentinc/cp-kafka:7.6.0 \
    kafka-topics --create --topic user-events \
    --bootstrap-server kafka:29092 \
    --replication-factor 1 --partitions 3
    ```

## 🧪 Running the Flink Job

1. Create Cassandra sink table

    ```bash
    - docker exec -it cassandra cqlsh
    - execute the sql from below file
        flink_app/cassandra-init/init.cql

2. Create Kafka source topic
       
    ```text
    follow topic creation steps in below file
    flink_app/kafka-init/new_topic.txt
     ```

3. Build your Flink job
    ```bash
     mvn clean package
    ```

4. Submit job
    ```bash
    docker exec -it jobmanager flink run com.event_streaming.Main /opt/flink/usrlib/flink_app-1.0-SNAPSHOT.jar
    ```

5. Open Flink UI: http://localhost:8088

## 📊 Verifying Data

Kafka UI → View topic messages

Cassandra:

Connect to Cassandra CLI:

```bash
docker exec -it cassandra cqlsh
```

Check keyspace and tables:

```bash
USE event_streaming;
SELECT * FROM user_events;
```

## 🧹 Stop Everything 

```bash
docker-compose down -v
```
