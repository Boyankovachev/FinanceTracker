package com.diplomna.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private final EmailConfig emailConfig;

    public EmailService(EmailConfig emailConfig){
        this.emailConfig = emailConfig;
    }

    private JavaMailSenderImpl getMailSender(){
        //Crete mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfig.getHost());
        mailSender.setPort(emailConfig.getPort());
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());
        return mailSender;
    }

    public void sendNotificationEmail(String userEmail, String notificationName, double notificationPrice){
        /*
        Send email for notification.
        userEmail - receiver
        notificationName - name of notification
        notificationPrice - price of notification
         */
        //get Mail sender
        JavaMailSenderImpl mailSender = getMailSender();

        //create mail instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("FinanceTracker@gmail.com");
        mailMessage.setTo(userEmail);
        mailMessage.setSubject(notificationName);
        String text = "Your notification: " + notificationName + ", has reached it's target price of " +
                String.valueOf(notificationPrice);
        mailMessage.setText(text);

        //send mail
        mailSender.send(mailMessage);
    }

    public void sendAuthenticationKeyEmail(String key, String userEmail){
        /*
            Send email for notification.
            userEmail - receiver
            key - 2fa code
         */
        //get Mail sender
        JavaMailSenderImpl mailSender = getMailSender();

        //create mail instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("FinanceTracker@gmail.com");
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Authentication Key");
        String text = "Your authentication key: " + key;
        mailMessage.setText(text);

        //send mail
        mailSender.send(mailMessage);
    }

}
