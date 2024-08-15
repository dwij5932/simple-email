package org.example;

import org.example.util.kafkaEmailConsumer;
import status.customer.email.Customer;
import status.enterprise.email.Enterprise;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
//        // SMTP server configuration
//        String host = "localhost";
//        int port = 1025; // Port MailHog or the debug server is listening on
//        String from = "from@example.com";
//        String to = "to@example.com";
//
//        // Set properties
//        Properties properties = System.getProperties();
//        properties.setProperty("mail.smtp.host", host);
//        properties.setProperty("mail.smtp.port", String.valueOf(port));
//
//        // Get the default Session object
//        Session session = Session.getDefaultInstance(properties);
//
//        try {
//            // Create a default MimeMessage object
//            MimeMessage message = new MimeMessage(session);
//
//            // Set the From, To, and Subject fields
//            message.setFrom(new InternetAddress(from));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject("Test Email");
//
//            // Set the actual message
//            message.setText("This is a test email sent through JavaMail API.");
//
//            // Send message
//            Transport.send(message);
//            System.out.println("Email sent successfully!");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }

        Thread thread1 = new Thread(new kafkaEmailConsumer<>("status.customer.email.0", Customer.class));
        Thread thread2 = new Thread(new kafkaEmailConsumer<>("status.enterprise.email.0", Enterprise.class));
        Thread thread3 = new Thread(new kafkaEmailConsumer<>("status.error.email.0", Error.class));

        System.out.println("Starting");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}