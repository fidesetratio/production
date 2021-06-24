````
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka-1:
    image: wurstmeister/kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka1:9092
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LOG_DIRS: /kafka/logs
      KAFKA_BROKER_ID: 500
      KAFKA_offsets_topic_replication_factor: 3
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - ${KAFKA_DATA}/500:/kafka

  kafka-2:
    image: wurstmeister/kafka
    ports:
      - "9093:9093"
    environment:
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka1:9093
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9093
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LOG_DIRS: /kafka/logs
      KAFKA_BROKER_ID: 501
      KAFKA_offsets_topic_replication_factor: 3
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - ${KAFKA_DATA}/501:/kafka

  kafka-3:
    image: wurstmeister/kafka
    ports:
      - "9094:9094"
    environment:
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka1:9094
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9094
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LOG_DIRS: /kafka/logs
      KAFKA_BROKER_ID: 502
      KAFKA_offsets_topic_replication_factor: 3
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - ${KAFKA_DATA}/502:/kafka
   
````



````
export KAFKA_DATA=/home/administrator/envcosmos/kafkadata
docker-compose -f kafka.yaml up -d

if you need down
docker-compose -f kafka.yaml down



tools
docker run -p 8080:8080 \
        -e KAFKA_CLUSTERS_0_NAME=local \
        -e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=128.21.24.62:9092 \
        -d provectuslabs/kafka-ui:latest
http://128.21.24.62:8080/ui/clusters/local/consumer-groups
````

