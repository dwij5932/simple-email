package org.example.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.config.KafkaConsumerConfiguration;
import status.customer.email.Customer;
import status.enterprise.email.Enterprise;

import java.time.Duration;
import java.util.Collections;

public class KafkaEmailConsumerEnterprices implements Runnable {

    private final KafkaConsumer<String, GenericRecord> consumer;
    private final String topic;
    ObjectMapper mapper;

    public KafkaEmailConsumerEnterprices(String topic) {
        this.consumer = new KafkaConsumer<>(KafkaConsumerConfiguration.getKafkaConsumerProperties());
        this.topic = topic;
        this.consumer.subscribe(Collections.singletonList(this.topic));
        this.mapper = new ObjectMapper();
    }

    @Override
    public void run() {
        try {
            while (true){
//                System.out.println("Consuming");
                ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, GenericRecord> record : records){
                    System.out.println("Record");
//                    T message = record.value();
                    // Customer customer = record.value();
                    Enterprise message = mapper.readValue(record.value().toString(), Enterprise.class);

                    System.out.println(message);

                    EmailSender.sendEmail("from@sysco.com",message.getEnterpriseEmail().toString(),"Order Conformation",
                            "Dear "+ message.getEnterpriseName()+
                                    ",\nYour Order No" + message.getOrderNumber() +
                                    " with Status "+ message.getOrderStatus() +
                                    " has been added successfully");
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            consumer.close();
        }
    }
}
