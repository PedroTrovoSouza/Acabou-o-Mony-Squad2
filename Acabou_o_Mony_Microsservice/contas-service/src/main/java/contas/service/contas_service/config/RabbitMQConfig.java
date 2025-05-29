package contas.service.contas_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queueContas() {
        return new Queue("queue_contas", true);
    }

    @Bean
    public DirectExchange contaExchange() {
        return new DirectExchange("conta_exchange");
    }

    @Bean
    public Binding bindingContas(Queue queueContas, DirectExchange contaExchange) {
        return BindingBuilder.bind(queueContas).to(contaExchange).with("routing_contas");
    }
}