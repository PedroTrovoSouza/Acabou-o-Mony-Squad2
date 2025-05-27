package contas.service.contas_service.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitConfig {

    // Nome da fila, exchange e routing key
    public static final String QUEUE_NAME = "email.queue";
    public static final String EXCHANGE_NAME = "email.exchange";
    public static final String ROUTING_KEY = "email.routingkey";

    // Define a fila (queue)
    @Bean
    public Queue emailQueue() {
        // durable = true mantém a fila após reiniciar o RabbitMQ
        return new Queue(QUEUE_NAME, true);
    }

    // Define a exchange (Direct: envia mensagens com base em uma routing key exata)
    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Cria o binding entre a fila e a exchange usando uma routing key
    @Bean
    public Binding binding(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder
                .bind(emailQueue)
                .to(emailExchange)
                .with(ROUTING_KEY);
    }
}
