# 🧑‍💻 Kafka User Event Producer

This is a Java-based Kafka producer that continuously sends randomized **user events** (like LOGIN, LOGOUT, PURCHASE, etc.) to a Kafka topic. It uses a modular design, is built with Maven, and reads configuration from a properties file.

---

## 📦 Features

- ✅ Modular Java code (config, model, producer, util)
- 🔁 Continuously sends events every 5 seconds
- 🎲 Randomized user IDs and event types
- ⚙️ Configurable via `application.properties`
- 🛠 Built using Apache Kafka client & Jackson

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 11+
- Apache Kafka running in docker (or locally)
- Maven installed (`mvn -v`)

### 📥 Clone the Repo

```bash
git clone git@github.com:rajatrao/flink_events_consumer.git
cd flink_events_consumer/activity_source
```

🔧 Configure Kafka Settings

Edit the src/main/resources/app.properties file:

```text
bootstrap.servers=localhost:9092
topic.name=user-events
```

Make sure the topic exists in Kafka:

- Follow `flink_app/README.md` for steps to create kafka topic

```bash
docker run --rm --network flink_app_default confluentinc/cp-kafka:7.6.0 \
kafka-topics --create --topic user-events \
--bootstrap-server kafka:29092 \
--replication-factor 1 --partitions 3
```

🛠 Build & Run
🧱 Compile the Project
```bash
mvn clean compile
```
▶️ Run the Producer
```bash
mvn exec:java -Dexec.mainClass=com.event_producer.Main
```

You should see events like below as part of kafka consume event process handling
```json
{
"user_id": "589e4567-e89b-12d3-a456-426614174003",
"event_type": "LOGIN",
"event_time": 1699999999999,
"event_id": "7f34aeee-e89b-12d3-a456-426614174111",
"metadata": {
        "source": "web",
        "session_id": "abc123-e89b-12d3-a456-426614174222"
    }
}
```