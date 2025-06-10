package com.example.transacao_service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TransacaoConsumer {

    @RabbitListener(queues = "queue_transacoes")
    public void processarTransacao(String message) {
        System.out.printf("✔ Mensagem recebida no transacao-service: %s%n", message);
    }
}