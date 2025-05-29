package com.example.transacao_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queueTransacoes() {
        return new Queue("queue_transacoes", true);
    }

    @Bean
    public DirectExchange transacaoExchange() {
        return new DirectExchange("transacao_exchange");
    }

    @Bean
    public Binding bindingTransacoes(Queue queueTransacoes, DirectExchange transacaoExchange) {
        return BindingBuilder.bind(queueTransacoes).to(transacaoExchange).with("routing_transacoes");
    }
}