package contas.service.contas_service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ContaConsumer {

    @RabbitListener(queues = "queue_contas")
    public void processarConta(String message) {
        System.out.printf("✔ Mensagem recebida no conta-service: %s%n", message);
    }
}