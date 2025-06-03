package com.acabou_o_mony.pedido.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.acabou_o_mony.pedido.entity.Pedido;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @RabbitListener(queues = "queue_pedidos")
    public void processarPedido(String mensagem) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Pedido pedido = objectMapper.readValue(mensagem, Pedido.class);
            System.out.println("Pedido recebido: " + pedido);
        } catch (Exception e) {
            System.err.println("Erro ao desserializar pedido: " + e.getMessage());
        }
    }
}