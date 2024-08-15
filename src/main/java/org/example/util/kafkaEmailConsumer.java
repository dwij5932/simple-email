package org.example.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.example.config.KafkaConsumerConfiguration;
import status.customer.email.Customer;

import java.time.Duration;
import java.util.Collections;

public class kafkaEmailConsumer<T> implements Runnable {

    private final KafkaConsumer<String, T> consumer;
    private final String topic;
    private final Class<T> avroClass;
    ObjectMapper mapper;

    public kafkaEmailConsumer(String topic, Class<T> avroClass) {
        this.consumer = new KafkaConsumer<>(KafkaConsumerConfiguration.getKafkaConsumerProperties());
        this.topic = topic;
        this.avroClass = avroClass;
        this.consumer.subscribe(Collections.singletonList(this.topic));
        this.mapper = new ObjectMapper();
    }

    @Override
    public void run() {
        try {
            while (true){
//                System.out.println("Consuming");
                ConsumerRecords<String, T> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, T> record : records){
                    System.out.println("Record");
//                    T message = record.value();
                   // Customer customer = record.value();
                    T message = mapper.readValue(record.value().toString(), avroClass);

                    System.out.println(message);
                    System.out.println(message.getClass());
                }
            }
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            consumer.close();
        }
    }
}
