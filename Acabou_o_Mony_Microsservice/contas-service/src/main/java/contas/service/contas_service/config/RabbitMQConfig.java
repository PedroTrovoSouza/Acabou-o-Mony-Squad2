package contas.service.contas_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Fila de contas
    @Bean
    public Queue queueContas() {
        return new Queue("queue_contas", true);
    }

    // Fila de e-mails
    @Bean
    public Queue queueEmails() {
        return new Queue("queue_emails", true);
    }

    // Exchange compartilhada
    @Bean
    public DirectExchange contaExchange() {
        return new DirectExchange("conta_exchange");
    }

    // Binding para contas
    @Bean
    public Binding bindingContas(Queue queueContas, DirectExchange contaExchange) {
        return BindingBuilder.bind(queueContas).to(contaExchange).with("routing_contas");
    }

    // Binding para e-mails
    @Bean
    public Binding bindingEmails(Queue queueEmails, DirectExchange contaExchange) {
        return BindingBuilder.bind(queueEmails).to(contaExchange).with("routing_emails");
    }
}