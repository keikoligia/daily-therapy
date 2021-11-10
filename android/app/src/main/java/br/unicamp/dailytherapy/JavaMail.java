package br.unicamp.dailytherapy;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

public class JavaMail
{

    public static void enviarEmail()
    {

        final String username = "maria.piups@gmail.com";
        final String password = "pips12345";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("maria.piups@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("ligia.kcarvalho@gmail.com")
            );
            message.setSubject("Testing Gmail tls");
            message.setText("Dear Mail Crawler,"
                    + "\n\n Please do not spam my email!");

            javax.mail.Transport transport = session.getTransport("smtp");
            transport.send(message,message.getAllRecipients());

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}