package com.acabou_o_mony.pedido.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @RabbitListener(queues = "queue_pedidos")
    public void processarPedido(String message) {
        System.out.printf("✔ Mensagem recebida no pedido-service: %s%n", message);
    }
}