package org.example.config;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;
import static org.example.config.KafkaConfiguration.*;

public class KafkaConsumerConfiguration {

    public static Properties getKafkaConsumerProperties(){
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        properties.put(KEY_DESERIALIZER_CLASS_CONFIG, KEY_DESERIALIZER_CLASS);
        properties.put(VALUE_DESERIALIZER_CLASS_CONFIG,  KafkaAvroDeserializer.class.getName());
        properties.put(SCHEMA_DEREGISTRY_URL_CONFIG, SCHEMA_DEREGISTRY_URL);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
//        properties.put("specific.avro.reader", "true");

        return properties;
    }
}
