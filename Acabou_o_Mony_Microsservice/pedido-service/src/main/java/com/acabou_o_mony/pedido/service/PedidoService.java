package com.acabou_o_mony.pedido.service;

import com.acabou_o_mony.pedido.config.WebClientConfig;
import com.acabou_o_mony.pedido.dto.*;
import com.acabou_o_mony.pedido.entity.Pedido;
import com.acabou_o_mony.pedido.enums.StatusPedido;
import com.acabou_o_mony.pedido.enums.StatusTransacao;
import com.acabou_o_mony.pedido.mapper.MapperPedido;
import com.acabou_o_mony.pedido.repository.PedidoRepository;
import com.acabou_o_mony.produto_service.dto.ProdutoResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import contas.service.contas_service.dto.cartao.CartaoResponseDTO;
import contas.service.contas_service.dto.cliente.ClienteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final WebClient webProduto;
    private final WebClient webClient;
    private final WebClient webTransacao;

    @Autowired
    public PedidoService(WebClient.Builder webClientBuilder){
        this.webProduto = webClientBuilder.baseUrl("http://localhost:9091").build();
        this.webClient = webClientBuilder.baseUrl("http://localhost:9092").build();
        this.webTransacao = webClientBuilder.baseUrl("http://localhost:9093").build();
    }

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MapperPedido mapperPedido;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    public PedidoResponseDTO buscarPedido(long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            throw new RuntimeException("Pedido com ID " + id + " não encontrado.");
        }

        Pedido pedido = pedidoOptional.get();

        try {
            String nomeProduto = pedido.getProduto();

            if (nomeProduto == null || nomeProduto.isBlank()) {
                throw new RuntimeException("O nome do produto está vazio ou nulo no pedido.");
            }

            webProduto.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(9091)
                            .path("/produtos/nome")
                            .queryParam("nome", nomeProduto)
                            .build())
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Produto " + pedido.getProduto() + " não encontrado no serviço de produtos.");
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Erro na comunicação com o serviço de produtos: " + e.getMessage());
        }

        return new PedidoResponseDTO(
                pedido.getProduto(),
                pedido.getValorTotal(),
                pedido.getDataPedido(),
                pedido.getStatus()
        );
    }

    public PedidoCartaoProdutoDTO cadastrarPedido(String nomeProduto, BuscarEmailPedidoDTO dto) throws JsonProcessingException {
        if (dto == null || dto.getPedido() == null) {
            throw new RuntimeException("Pedido não pode ser nulo");
        }

        String url = "/clientes/email?email=" + dto.getLogin();

        try {
            ClienteDto cliente = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(ClienteDto.class)
                    .block();

        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Email: " + dto.getLogin() + " não encontrado.");
        }

        String urlProduto = "/produtos/nome?nome=" + nomeProduto;

        try {
            List<ProdutoResponseDTO> produtos = webProduto.get()
                    .uri(urlProduto)
                    .retrieve()
                    .bodyToFlux(ProdutoResponseDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Produto: " + nomeProduto + " não encontrado");
        }

        String urlCartao = "/cartao/" + dto.getPedido().getCartao();

        try {
            CartaoResponseDTO cartao = webClient.get()
                    .uri(urlCartao)
                    .retrieve()
                    .bodyToMono(CartaoResponseDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Cartão com ID " + dto.getPedido().getCartao() + " não encontrado.");
        }

        TransacaoRequestDTO transacaoRequest = new TransacaoRequestDTO();
        transacaoRequest.setTipoTransacao("DEBITO");
        transacaoRequest.setValor(dto.getPedido().getValorTotal());
        transacaoRequest.setStatus(StatusTransacao.SUCESSO);
        transacaoRequest.setClienteRemetenteId(dto.getClienteRemetenteId());
        transacaoRequest.setClienteDestinatarioId(dto.getClienteDestinatarioId());
        transacaoRequest.setCartaoId(dto.getPedido().getCartao());

        System.out.println(new ObjectMapper().writeValueAsString(transacaoRequest));

        String urlTransacao = "/transacao"; // apenas o endpoint, sem concatenar o objeto

        try {

            TransacaoResponseDTO transacao = webTransacao.post()
                    .uri(urlTransacao)
                    .bodyValue(transacaoRequest) // envia no corpo
                    .retrieve()
                    .bodyToMono(TransacaoResponseDTO.class)
                    .block();

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Erro ao cadastrar transação: " + e.getMessage(), e);
        }

        Pedido pedido = mapperPedido.toEntity(dto.getPedido());
        pedido.setProduto(nomeProduto);
        pedido.setDataPedido(new Date());
        pedido.setCartao(dto.getPedido().getCartao());

        if (transacaoRequest != null && transacaoRequest.getStatus().equals(StatusTransacao.SUCESSO)) {
            pedido.setStatus(StatusPedido.APROVADO);
        } else {
            pedido.setStatus(StatusPedido.RECUSADO);
        }

        Pedido salvo = pedidoRepository.save(pedido);

        logger.info("Pedido criado: ID={}, Produto={}, Status={}", salvo.getIdPedido(), salvo.getProduto(), salvo.getStatus());

        ObjectMapper objectMapper = new ObjectMapper();
        String pedidoJson = objectMapper.writeValueAsString(pedido);
        rabbitTemplate.convertAndSend("pedido_exchange", "routing_pedidos", pedidoJson);

        return mapperPedido.toPedidoCartaoProdutoDTO(salvo);
    }

    public Optional<Pedido> deletePedido(long id) {
        Optional<Pedido> pedidoEncontrado = pedidoRepository.findById(id);
        if (pedidoEncontrado.isEmpty()) {
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
