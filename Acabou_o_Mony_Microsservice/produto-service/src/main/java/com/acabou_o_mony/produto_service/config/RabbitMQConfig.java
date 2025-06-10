package com.acabou_o_mony.produto_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queueProdutos() {
        return new Queue("queue_produtos", true);
    }

    @Bean
    public DirectExchange produtoExchange() {
        return new DirectExchange("produto_exchange");
    }

    @Bean
    public Binding bindingProdutos(Queue queueProdutos, DirectExchange produtoExchange) {
        return BindingBuilder.bind(queueProdutos).to(produtoExchange).with("routing_produtos");
    }
}