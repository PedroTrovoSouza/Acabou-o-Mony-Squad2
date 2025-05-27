package contas.service.contas_service.webclient;

import contas.service.contas_service.dto.cartao.CartaoResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TransacaoCartaoClient {

    private final WebClient webClient;

    public TransacaoCartaoClient(WebClient.Builder builder){
        this.webClient = builder.baseUrl("localhost:9090/").build();
    }

    public CartaoResponseDTO obterCartaoPorId(Long id){
        return webClient.get()
                .uri("/cartao/{id}", id)
                .retrieve()
                .bodyToMono(CartaoResponseDTO.class)
                .block();
    }

}
