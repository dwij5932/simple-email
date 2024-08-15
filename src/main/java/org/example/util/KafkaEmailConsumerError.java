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
import status.error.email.Error;

import java.time.Duration;
import java.util.Collections;

public class KafkaEmailConsumerError implements Runnable {

    private final KafkaConsumer<String, GenericRecord> consumer;
    private final String topic;
    ObjectMapper mapper;

    public KafkaEmailConsumerError(String topic) {
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
                    Error message = mapper.readValue(record.value().toString(), Error.class);

                    System.out.println(message);

                    EmailSender.sendEmail("from@sysco.com","error@sysco.com","Order Confermation",
                            "Dear Admin,\n"+
                            "Order Number "+ message.getOrderNumber()+" with the status of "
                            + message.getOrderNumber() +" has been failed to added to the system.");
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            consumer.close();
        }
    }
}
