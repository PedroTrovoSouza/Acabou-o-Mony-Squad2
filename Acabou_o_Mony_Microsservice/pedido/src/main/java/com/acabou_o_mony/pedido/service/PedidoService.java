package com.acabou_o_mony.pedido.service;

import com.acabou_o_mony.pedido.dto.*;
import com.acabou_o_mony.pedido.entity.EmailMessage;
import com.acabou_o_mony.pedido.entity.Pedido;
import com.acabou_o_mony.pedido.enums.StatusPedido;
import com.acabou_o_mony.pedido.mapper.MapperPedido;
import com.acabou_o_mony.pedido.repository.PedidoRepository;
import contas.service.contas_service.dto.cliente.ClienteDto;
import contas.service.contas_service.entity.Cartao;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.acabou_o_mony.pedido.enums.StatusTransacao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    MapperPedido mapperPedido;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public PedidoResponseDTO buscarPedido(long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);

        if (pedidoOptional.isEmpty()) {
            throw new RuntimeException("Pedido com ID " + id + " não encontrado.");
        }

        Pedido pedido = pedidoOptional.get();

        WebClient client = WebClient.create();

        try {
            client.get()
                    .uri("http://localhost:9091/produto/{nome}", pedido.getProduto())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Produto " + pedido.getProduto() + " não encontrado no serviço de produtos.");
        }

        return new PedidoResponseDTO(
                pedido.getProduto(),
                pedido.getValorTotal(),
                pedido.getDataPedido(),
                pedido.getStatus()
        );
    }

    public PedidoCartaoProdutoDTO cadastrarPedido(String nomeProduto, BuscarEmailPedido dto) {
        if (dto == null) {
            throw new RuntimeException("Pedido não pode ser nulo");
        }

        WebClient client = WebClient.create();

        ClienteDto cliente;
        try {
            cliente = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(9092)
                            .path("/clientes/email")
                            .queryParam("email", dto.getEmail())
                            .build())
                    .retrieve()
                    .bodyToMono(ClienteDto.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Email: " + dto.getEmail() + " não encontrado.");
        }

        try {
            client.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(9091)
                            .path("/produto/nome")
                            .queryParam("nome", nomeProduto)
                            .build())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Produto: " + nomeProduto + " não encontrado");
        }
        Cartao cartao;
        try {
            client.get()
                    .uri("http://localhost:9092/cartao/{id}", dto.getPedido().getCartao())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Cartão com ID " + dto.getPedido().getCartao() + " não encontrado.");
        }

        TransacaoRequestDTO transacaoRequest = new TransacaoRequestDTO();
        transacaoRequest.setTipoTransacao("DEBITO");
        transacaoRequest.setValor(dto.getPedido().getValorTotal());
        transacaoRequest.setDthora(LocalDateTime.now());
        transacaoRequest.setStatus(StatusTransacao.SUCESSO);
        transacaoRequest.setClienteRemetenteId(dto.getClienteRemetenteId());
        transacaoRequest.setClienteDestinatarioId(dto.getClienteDestinatarioId());
        transacaoRequest.setCartaoId(dto.getPedido().getCartao());

        TransacaoResponseDTO transacao;
        try {
            transacao = client.post()
                    .uri("http://localhost:9093/transacao")
                    .bodyValue(transacaoRequest)
                    .retrieve()
                    .bodyToMono(TransacaoResponseDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Erro ao consultar transação: " + e.getMessage());
        }

        Pedido pedido = mapperPedido.toEntity(dto.getPedido());
        pedido.setDataPedido(new Date());
        pedido.setProduto(nomeProduto);
        pedido.setCartao(dto.getPedido().getCartao());

        if (transacao != null && "APROVADA".equalsIgnoreCase(transacao.getTransacao())) {
            pedido.setStatus(StatusPedido.APROVADO);
        } else {
            pedido.setStatus(StatusPedido.RECUSADO);
        }

        Pedido salvo = pedidoRepository.save(pedido);

        EmailMessage emailMessage = new EmailMessage(
                cliente.email(),
                "Criação de Conta na Acabou o Mony",
                "Parabéns! Sua conta na Acabou o Mony foi criada com sucesso!!"
        );

        rabbitTemplate.convertAndSend("conta_exchange", "routing_emails", emailMessage);

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
