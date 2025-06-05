package com.acabou_o_mony.pedido.service;

import com.acabou_o_mony.pedido.dto.*;
import com.acabou_o_mony.pedido.entity.Pedido;
import com.acabou_o_mony.pedido.enums.StatusPedido;
import com.acabou_o_mony.pedido.mapper.MapperPedido;
import com.acabou_o_mony.pedido.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.*;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private MapperPedido mapperPedido;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.build()).thenReturn(webClient);
    }

    @Test
    void testBuscarPedidoComSucesso() {
        Pedido pedido = new Pedido();
        pedido.setProduto("Produto Teste");
        pedido.setValorTotal(100.0);
        pedido.setDataPedido(new Date());
        pedido.setStatus(StatusPedido.APROVADO);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity()).thenReturn(Mono.just(ResponseEntity.ok().build()));

        PedidoResponseDTO response = pedidoService.buscarPedido(1L);

        assertEquals("Produto Teste", response.getNomeProduto());
        assertEquals(100.0, response.getValorTotal());
        assertEquals(StatusPedido.APROVADO, response.getStatus());
    }

    @Test
    void testBuscarPedidoNaoEncontrado() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> pedidoService.buscarPedido(1L));
        assertTrue(exception.getMessage().contains("não encontrado"));
    }

    @Test
    void testDeletePedidoComSucesso() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Optional<Pedido> resultado = pedidoService.deletePedido(1L);

        verify(pedidoRepository, times(1)).delete(pedido);
        assertTrue(resultado.isPresent());
    }

    @Test
    void testDeletePedidoNaoEncontrado() {
        when(pedidoRepository.findById(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> pedidoService.deletePedido(1L));
    }

    @Test
    void testPatchStatusPedidoComSucesso() {
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.RECUSADO);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        StatusPedido novoStatus = pedidoService.patchStatusPedido(StatusPedido.APROVADO, 1L);

        assertEquals(StatusPedido.APROVADO, novoStatus);
    }

    @Test
    void testPatchStatusPedidoNaoEncontrado() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pedidoService.patchStatusPedido(StatusPedido.APROVADO, 1L));
    }

    @Test
    void testCadastrarPedidoComDtoNulo() {
        assertThrows(RuntimeException.class, () -> pedidoService.cadastrarPedido("Produto Teste", null));
    }
}
