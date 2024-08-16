package org.example;

import org.example.util.KafkaEmailConsumerEnterprices;
import org.example.util.KafkaEmailConsumerError;
import org.example.util.kafkaEmailConsumerCustomer;
import status.customer.email.Customer;
import status.enterprise.email.Enterprise;

public class Main {
    public static void main(String[] args) {

        Thread thread1 = new Thread(new kafkaEmailConsumerCustomer("status.customer.email.0"));
        Thread thread2 = new Thread(new KafkaEmailConsumerEnterprices("status.enterprise.email.0"));
        Thread thread3 = new Thread(new KafkaEmailConsumerError("status.error.email.0"));

        System.out.println("Starting");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Application Stop");
    }
}