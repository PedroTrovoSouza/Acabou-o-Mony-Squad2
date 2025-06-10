package com.acabou_o_mony.pedido.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // Fila de pedidos
    @Bean
    public Queue queuePedidos() {
        return new Queue("queue_pedidos", true);
    }

    // Fila de e-mails
    @Bean
    public Queue queueEmails() {
        return new Queue("queue_emails", true);
    }

    // Exchange compartilhada
    @Bean
    public DirectExchange pedidoExchange() {
        return new DirectExchange("pedido_exchange");
    }

    // Binding para pedidos
    @Bean
    public Binding bindingPedidos(Queue queuePedidos, DirectExchange pedidoExchange) {
        return BindingBuilder.bind(queuePedidos).to(pedidoExchange).with("routing_pedidos");
    }

    // Binding para e-mails
    @Bean
    public Binding bindingEmails(Queue queueEmails, DirectExchange pedidoExchange) {
        return BindingBuilder.bind(queueEmails).to(pedidoExchange).with("routing_emails");
    }

    // Conversor de mensagens JSON para RabbitMQ
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Configuração do RabbitTemplate para usar JSON
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}