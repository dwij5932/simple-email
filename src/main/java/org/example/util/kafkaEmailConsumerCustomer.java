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
import java.time.Duration;
import java.util.Collections;

public class kafkaEmailConsumerCustomer implements Runnable {

    private final KafkaConsumer<String, GenericRecord> consumer;
    private final String topic;
    ObjectMapper mapper;

    public kafkaEmailConsumerCustomer(String topic) {
        this.consumer = new KafkaConsumer<>(KafkaConsumerConfiguration.getKafkaConsumerProperties());
        this.topic = topic;
        this.consumer.subscribe(Collections.singletonList(this.topic));
        this.mapper = new ObjectMapper();
    }

    @Override
    public void run() {
        try {
            while (true){
                ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, GenericRecord> record : records){
                    System.out.println("Record");

                    Customer message = mapper.readValue(record.value().toString(), Customer.class);

                    System.out.println(message);

                    EmailSender.sendEmail("from@sysco.com",message.getCustomerEmail().toString(),"Order Conformation",
                            "Dear "+ message.getCustomerName()+
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
