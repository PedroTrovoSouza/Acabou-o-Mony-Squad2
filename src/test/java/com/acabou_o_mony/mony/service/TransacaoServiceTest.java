package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.entity.Cartao;
import com.acabou_o_mony.mony.entity.Conta;
import com.acabou_o_mony.mony.entity.Transacao;
import com.acabou_o_mony.mony.enums.Status;
import com.acabou_o_mony.mony.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarTransacaoComSucesso() {
        // Arrange
        Conta conta = new Conta();
        conta.setSaldo(new BigDecimal("1000.00"));

        Cartao cartao = new Cartao();
        cartao.setConta(conta);

        Transacao transacao = new Transacao();
        transacao.setValor(200.00);
        transacao.setCartao(cartao);
        transacao.setDthora(LocalDateTime.now());

        Transacao transacaoComStatus = new Transacao();
        transacaoComStatus.setValor(200.00);
        transacaoComStatus.setCartao(cartao);
        transacaoComStatus.setDthora(transacao.getDthora());
        transacaoComStatus.setStatus(Status.SUCESSO);

        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacaoComStatus);

        // Act
        Transacao resultado = transacaoService.salvar(transacao);

        // Assert
        assertEquals(Status.SUCESSO, resultado.getStatus());
        assertEquals(new BigDecimal("800.00"), conta.getSaldo()); // 1000 - 200
        verify(transacaoRepository, times(1)).save(any(Transacao.class));
    }

    @Test
    void deveListarTodasAsTransacoes() {
        // Arrange
        Transacao transacao1 = new Transacao();
        transacao1.setId(1L);
        transacao1.setValor(150.0);

        Transacao transacao2 = new Transacao();
        transacao2.setId(2L);
        transacao2.setValor(300.0);

        List<Transacao> mockTransacoes = List.of(transacao1, transacao2);

        when(transacaoRepository.findAll()).thenReturn(mockTransacoes);

        // Act
        List<Transacao> resultado = transacaoService.listarTodasTransacoes();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals(150.0, resultado.get(0).getValor());
        assertEquals(300.0, resultado.get(1).getValor());
        verify(transacaoRepository, times(1)).findAll();
    }

    public Transacao listarTransacaoPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("ID inválido.");
        }

        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacao não encontrado."));

        return transacao;
    }
}