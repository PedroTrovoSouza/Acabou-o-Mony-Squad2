package contas.service.contas_service.rabbitmq;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService (JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("qualquercoisa@exemplo.com");
        message.setSubject("Teste com Mailtrap");
        message.setText("Este é um e-mail enviado usando o Mailtrap!");
        message.setFrom("fernando@passaralhos.com"); // opcional se já configurado no application.properties
        mailSender.send(message);
    }
}
