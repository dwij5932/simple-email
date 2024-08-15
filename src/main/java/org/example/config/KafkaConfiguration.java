package org.example.config;

public class KafkaConfiguration {

    private static KafkaConfiguration INSTANCE;

    public static final String BOOTSTRAP_SERVERS_CONFIG = "bootstrap.servers";
    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String KEY_DESERIALIZER_CLASS_CONFIG = "key.deserializer";
    public static final String KEY_DESERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringDeserializer";
    public static final String VALUE_DESERIALIZER_CLASS_CONFIG = "value.deserializer";
    public static final String VALUE_DESERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringDeserializer";
    public static final String SCHEMA_DEREGISTRY_URL_CONFIG = "schema.registry.url";
    public static final String SCHEMA_DEREGISTRY_URL = "http://localhost:8081";
}
