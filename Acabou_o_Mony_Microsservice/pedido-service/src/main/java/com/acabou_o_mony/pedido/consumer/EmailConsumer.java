package com.acabou_o_mony.pedido.consumer;

import com.acabou_o_mony.pedido.entity.EmailMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final JavaMailSender mailSender;

    public EmailConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "queue_emails")
    public void receiveMessage(EmailMessage email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());

        mailSender.send(message);
        System.out.println("📩 E-mail enviado para: " + email.getTo());
    }
}
