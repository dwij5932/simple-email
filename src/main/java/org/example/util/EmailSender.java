package org.example.util;

import org.example.config.EmailConfiguration;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static void sendEmail(String from, String to, String subject, String body) {
        Session session = Session.getDefaultInstance(EmailConfiguration.getEmailProperties());

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
