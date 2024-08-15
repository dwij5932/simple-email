package org.example.config;

import java.util.Properties;

public class EmailConfiguration {

    public static Properties getEmailProperties(){
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "localhost");
        properties.setProperty("mail.smtp.port", String.valueOf(1025));
        return properties;
    }
}
