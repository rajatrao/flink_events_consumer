CREATE KEYSPACE IF NOT EXISTS event_streaming WITH REPLICATION = {
  'class': 'SimpleStrategy',
  'replication_factor': 1
};

USE event_streaming;

CREATE TABLE IF NOT EXISTS user_events (
  user_id UUID,
  bucket_date TEXT,
  event_time BIGINT,
  event_type TEXT,
  event_id UUID,
  metadata TEXT,
  PRIMARY KEY ((user_id, bucket_date), event_time, event_type)
)WITH CLUSTERING ORDER BY (event_time DESC, event_type ASC);

