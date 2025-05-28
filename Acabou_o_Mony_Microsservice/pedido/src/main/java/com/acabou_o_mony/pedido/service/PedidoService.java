package com.acabou_o_mony.pedido.service;

import com.acabou_o_mony.pedido.dto.*;
import com.acabou_o_mony.pedido.entity.Pedido;
import com.acabou_o_mony.pedido.enums.StatusPedido;
import com.acabou_o_mony.pedido.mapper.MapperPedido;
import com.acabou_o_mony.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    MapperPedido mapperPedido;

    public PedidoResponseDTO buscarPedido(long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        WebClient client = WebClient.create();

        var resposta = client.get()
                .uri("http://localhost:9091/produto/{nome}")
                .retrieve()
                .toBodilessEntity()
                .block();

        return null;
    }

    public PedidoCartaoProdutoDTO cadastrarPedido(BuscarEmailPedido dto) {
        if (dto == null) {
            throw new RuntimeException("Pedido não pode ser nulo");
        }

        WebClient client = WebClient.create();

        try {
            client.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(9092)
                            .path("/clientes/email")
                            .queryParam("email", dto.getEmail())
                            .build())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Email: " + dto.getEmail() + " não encontrado.");
        }

        /*try {
            client.get()
                    .uri("http://localhost:9091/produto/{nome}", dto.getPedido().getProduto())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Produto " + dto.getPedido().getProduto() + " não encontrado.");
        }

        try {
            client.get()
                    .uri("http://localhost:9092/cartao/{id}", dto.getPedido().getCartao())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Cartão com ID " + dto.getPedido().getCartao() + " não encontrado.");
        }

        TransacaoResponseDTO transacao;
        try {
            transacao = client.post()
                    .uri("http://localhost:9093/transacao")
                    .retrieve()
                    .bodyToMono(TransacaoResponseDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Erro ao consultar transação: " + e.getMessage());
        }*/

        Pedido pedido = mapperPedido.toEntity(dto.getPedido());
        pedido.setDataPedido(new Date());
        pedido.setProduto(dto.getPedido().getProduto());
        pedido.setCartao(dto.getPedido().getCartao());

        /*if (transacao != null && "APROVADA".equalsIgnoreCase(String.valueOf(transacao.getStatus()))) {
            pedido.setStatus(StatusPedido.APROVADO);
        } else {
            pedido.setStatus(StatusPedido.RECUSADO);
        }*/

        Pedido salvo = pedidoRepository.save(pedido);

        return mapperPedido.toPedidoCartaoProdutoDTO(salvo);
    }

    public Optional<Pedido> deletePedido(long id) {
        Optional<Pedido> pedidoEncontrado = pedidoRepository.findById(id);

        if (pedidoEncontrado == null) {
            throw new RuntimeException("Pedido não encontrado");
        }

        pedidoRepository.delete(pedidoEncontrado.get());
        return pedidoEncontrado;
    }

    public StatusPedido patchStatusPedido(StatusPedido status, Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);

        if (pedidoOptional.isEmpty()) {
            throw new RuntimeException("Pedido não encontrado com ID: " + id);
        }

        Pedido pedido = pedidoOptional.get();
        pedido.setStatus(status);

        return pedidoRepository.save(pedido).getStatus();
    }
}
