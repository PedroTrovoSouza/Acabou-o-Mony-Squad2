package com.acabou_o_mony.produto_service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConsumer {

    @RabbitListener(queues = "queue_produtos")
    public void processarProduto(String message) {
        System.out.printf("✔ Mensagem recebida no produto-service: %s%n", message);
    }
}