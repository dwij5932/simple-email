//package org.example;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import io.confluent.kafka.serializers.KafkaAvroDeserializer;
//import org.apache.avro.generic.GenericRecord;
//
//import java.time.Duration;
//import java.util.Collections;
//import java.util.Properties;
//
//public class AvroKafkaConsumer {
//    public static void main(String[] args) {
//        // Define the consumer configurations
//        Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Change if necessary
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
//        props.put("schema.registry.url", "http://localhost:8081"); // Schema Registry URL
//       // props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Consume from beginning if no offset is stored
//
//        // Create the consumer
//        KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<>(props);
//
//        // Subscribe to the Kafka topic
//        consumer.subscribe(Collections.singletonList("dom.order.status.0"));
//
//        // Poll and consume messages
//        try {
//            while (true) {
//                ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(100));
//                for (ConsumerRecord<String, GenericRecord> record : records) {
//                    System.out.printf("Consumed record with key %s and value %s%n", record.key(), record.value());
//                }
//            }
//        } finally {
//            consumer.close();
//        }
//    }
//}
