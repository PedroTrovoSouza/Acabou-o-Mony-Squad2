package com.acabou_o_mony.mony.controller;

import com.acabou_o_mony.mony.entity.*;
import com.acabou_o_mony.mony.enums.Status;
import com.acabou_o_mony.mony.repository.CartaoRepository;
import com.acabou_o_mony.mony.repository.TransacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransacaoControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private TransacaoRepository repository;
//
//    @Autowired
//    private CartaoRepository cartaoRepository;
//
//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private Transacao transacaoSalva;
//
//    @BeforeEach
//    public void setupPessoaFisica() {
//        // Cria e salva o cartão
//        Cartao cartao = new Cartao();
//        cartao.setNumero("1234-5678-9876-5432");
//        cartao.setVencimento(LocalDate.of(2030, 12, 31));
//        cartao.setNome("Meu cartão");
//
//        cartaoRepository.save(cartao);
//
//        Cliente clienteFisico = new PessoaFisica();
//
//        clienteRepository.save(clienteFisico);
//        Transacao transacao = new Transacao();
//        transacao.setTipo_transacao("COMPRA");
//        transacao.setValor(100.0);
//        transacao.setDthora(LocalDateTime.now());
//        transacao.setDestinatario(12345);
//        transacao.setStatus(Status.SUCESSO);
//        transacao.setRemetente(clienteFisico);
//        transacao.setCartao(cartao);
//
//        repository.save(transacao);
//    }
//
//    @Test
//    void deveCadastrarTransacaoComSucesso() throws Exception {
//        Conta conta = new Conta();
//        conta.setSaldo(new BigDecimal("500"));
//
//        Cartao cartao = new Cartao();
//        cartao.setConta(conta);
//
//        Transacao novaTransacao = new Transacao();
//        novaTransacao.setValor(200.0);
//        novaTransacao.setCartao(cartao);
//        novaTransacao.setDthora(LocalDateTime.now());
//
//        mockMvc.perform(post("/transacao")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(novaTransacao)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.valor").value(200.0));
//    }
//
//    @Test
//    void deveListarTodasTransacoesComSucesso() throws Exception {
//        mockMvc.perform(get("/transacao"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(transacaoSalva.getId()));
//    }
//
//    @Test
//    void deveListarTransacaoPorIdComSucesso() throws Exception {
//        mockMvc.perform(get("/transacao/" + transacaoSalva.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(transacaoSalva.getId()));
//    }
}
