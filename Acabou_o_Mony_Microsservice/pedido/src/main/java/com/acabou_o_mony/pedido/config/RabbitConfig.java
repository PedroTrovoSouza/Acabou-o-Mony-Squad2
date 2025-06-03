package com.acabou_o_mony.pedido.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    // Fila de contas
    @Bean
    public Queue queuePedidos() {
        return new Queue("queue_pedidos", true) {
        };
    }
    @Bean
    public Queue queueEmails() {
        return new Queue("queue_emails", true);
    }

    // Exchange compartilhada
    @Bean
    public DirectExchange pedidoExchange() {
        return new DirectExchange("pedido_exchange");
    }

    // Binding para contas
    @Bean
    public Binding bindingPedidos(Queue queuePedidos, DirectExchange pedidoExchange) {
        return BindingBuilder.bind(queuePedidos).to(pedidoExchange).with("routing_pedidos");
    }

    // Binding para e-mails
    @Bean
    public Binding bindingEmails(Queue queueEmails, DirectExchange pedidoExchange) {
        return BindingBuilder.bind(queueEmails).to(pedidoExchange).with("routing_emails");
    }
}
