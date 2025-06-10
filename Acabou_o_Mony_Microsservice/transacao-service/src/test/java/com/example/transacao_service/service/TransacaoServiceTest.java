package com.example.transacao_service.service;

import com.example.transacao_service.dto.conta.ContaDTO;
import com.example.transacao_service.dto.transacao.TransacaoRequestDTO;
import com.example.transacao_service.dto.transacao.TransacaoResponseDTO;
import com.example.transacao_service.entity.Transacao;
import com.example.transacao_service.enums.StatusTransacao;
import com.example.transacao_service.enums.TipoTransacao;
import com.example.transacao_service.mapper.TransacaoMapper;
import com.example.transacao_service.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class) // Ativa os mocks
class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService service;

    @Mock
    private TransacaoRepository repository;

    @Mock
    private TransacaoMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarTransacaoQuandoIdValido() {
        // Arrange
        Long id = 1L;
        Transacao transacao = new Transacao();
        transacao.setId(id);
        transacao.setValor(100.0);
        transacao.setTipo_transacao(TipoTransacao.DEBITO);

        TransacaoResponseDTO responseDTO = new TransacaoResponseDTO();
        responseDTO.setValor(100.0);
        responseDTO.setTipoTransacao("DEBITO");

        // Criação dos mocks
        TransacaoRepository repository = mock(TransacaoRepository.class);
        TransacaoMapper mapper = mock(TransacaoMapper.class);
        TransacaoService service = new TransacaoService(repository, null, mapper); // injeta mocks

        when(repository.findById(id)).thenReturn(Optional.of(transacao));
        when(mapper.toResponseDTO(transacao)).thenReturn(responseDTO);

        // Act
        TransacaoResponseDTO result = service.listarTransacaoPorId(id);

        // Assert
        assertNotNull(result);
        assertEquals(100.0, result.getValor());
        assertEquals("DEBITO", result.getTipoTransacao());
    }


    @Test
    void deveLancarExcecaoQuandoTransacaoNaoEncontrada() {
        Long id = 999L;

        lenient().when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.listarTransacaoPorId(id);
        });

        assertEquals("Transacao não encontrado.", exception.getMessage());
    }

    @Test
    void deveChamarApiAtualizarSaldoComSucesso() {
        // Mocks
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        // Encadeamento do WebClient
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString(), Optional.ofNullable(any()))).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        TransacaoService service = new TransacaoService(null, webClient, null);

        // Execução
        service.atualizarSaldo(123L, BigDecimal.valueOf(50.0));

        // Verificação (verifica se o fluxo foi chamado corretamente)
        verify(webClient).put();
        verify(requestBodyUriSpec).uri("http://localhost:9092/contas/saldo/{id}", 123L);
        verify(requestBodySpec).bodyValue(50.0);
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(Void.class);
    }

    @Test
    void deveLancarExcecaoQuandoWebClientFalhaAoAtualizarSaldo() {
        // Mocks do WebClient e do encadeamento
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        // Simulação do fluxo
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString(), Optional.ofNullable(any()))).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // ❗ Simulando exceção ao obter resposta
        when(responseSpec.bodyToMono(Void.class)).thenThrow(new RuntimeException("Erro ao atualizar saldo"));

        // Instância do serviço com mocks
        TransacaoService service = new TransacaoService(null, webClient, null);

        // Execução e verificação da exceção
        assertThrows(RuntimeException.class, () ->
                        service.atualizarSaldo(123L, BigDecimal.valueOf(50.0)),
                "Erro ao atualizar saldo"
        );

        // Verifica se chegou a tentar a requisição
        verify(webClient).put();
        verify(requestBodyUriSpec).uri("http://localhost:9092/contas/saldo/{id}", 123L);
        verify(requestBodySpec).bodyValue(50.0);
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(Void.class);
    }

    @Test
    void deveRetornarSaldoQuandoContaValida() {
        // Mocks
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        BigDecimal saldoEsperado = BigDecimal.valueOf(250.00);
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setSaldo(saldoEsperado);

        // Configura o fluxo do WebClient
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("http://localhost:9092/contas/{id}", 1L)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ContaDTO.class)).thenReturn(Mono.just(contaDTO));

        TransacaoService service = new TransacaoService(null, webClient, null);

        // Execução
        BigDecimal saldo = service.buscarSaldo(1L);

        // Verificação
        assertNotNull(saldo);
        assertEquals(saldoEsperado, saldo);
    }

    @Test
    void deveLancarExcecaoQuandoContaNaoForEncontradaOuSaldoForNulo() {
        // Mocks
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        // Configura fluxo do WebClient com Mono.empty() ao invés de Mono.just(null)
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("http://localhost:9092/contas/{id}", 999L)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ContaDTO.class)).thenReturn(Mono.empty());

        TransacaoService service = new TransacaoService(null, webClient, null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarSaldo(999L);
        });

        assertEquals("Conta não encontrada ou saldo inválido.", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoAoSalvarTransacaoComTipoInvalido() {
        // Arrange
        TransacaoRequestDTO dto = new TransacaoRequestDTO();
        dto.setClienteRemetenteId(1L);
        dto.setClienteDestinatarioId(2L);
        dto.setValor(100.0);
        dto.setTipoTransacao("TRANSFERENCIA"); // Tipo inválido
        dto.setCartaoId(123L); // Necessário para passar na primeira validação

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.salvar(dto);
        });

        assertEquals("Tipo de transação inválido", ex.getMessage());
    }
}
