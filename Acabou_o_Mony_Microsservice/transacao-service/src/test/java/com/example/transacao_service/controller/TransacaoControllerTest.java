package com.example.transacao_service.controller;

import com.example.transacao_service.dto.transacao.TransacaoRequestDTO;
import com.example.transacao_service.dto.transacao.TransacaoResponseDTO;
import com.example.transacao_service.enums.StatusTransacao;
import com.example.transacao_service.service.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransacaoController.class)
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransacaoService service;

    @Autowired
    private ObjectMapper objectMapper;


    // TESTE ENDPOINT - POST: Cenario Postivo
    @Test
    void deveCadastrarTransacaoComSucesso() throws Exception {
        // ARRANGE
        TransacaoRequestDTO requestDTO = new TransacaoRequestDTO();
        requestDTO.setTipoTransacao("DEBITO");
        requestDTO.setValor(150.0);
        requestDTO.setDthora(LocalDateTime.now());
        requestDTO.setStatus(StatusTransacao.SUCESSO);
        requestDTO.setClienteDestinatarioId(2L);
        requestDTO.setClienteRemetenteId(1L);
        requestDTO.setCartaoId(3L);

        TransacaoResponseDTO responseDTO = new TransacaoResponseDTO();
        responseDTO.setTipoTransacao("DEBITO");
        responseDTO.setValor(150.0);
        responseDTO.setDthora(requestDTO.getDthora());
        responseDTO.setStatus(StatusTransacao.SUCESSO);
        responseDTO.setClienteDestinatarioId(2L);
        responseDTO.setClienteRemetenteId(1L);
        responseDTO.setCartaoId(3L);

        // ACT
        when(service.salvar(any(TransacaoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoTransacao").value("DEBITO"))
                .andExpect(jsonPath("$.valor").value(150.0))
                .andExpect(jsonPath("$.clienteDestinatarioId").value(2))
                .andExpect(jsonPath("$.clienteRemetenteId").value(1))
                .andExpect(jsonPath("$.cartaoId").value(3));
    }

    // TESTES ENDPOINT - POST: Cenario Negativo
    @Test
    void deveFalharQuandoTentarCadastrarTransacaoComTipoTransacaoInvalido() throws Exception {
        TransacaoRequestDTO requestDTO = new TransacaoRequestDTO();
        requestDTO.setTipoTransacao("PIX");
        requestDTO.setValor(150.0);
        requestDTO.setDthora(LocalDateTime.now());
        requestDTO.setStatus(StatusTransacao.SUCESSO);
        requestDTO.setClienteDestinatarioId(2L);
        requestDTO.setClienteRemetenteId(1L);
        requestDTO.setCartaoId(3L);

        TransacaoResponseDTO responseDTO = new TransacaoResponseDTO();
        responseDTO.setTipoTransacao("PIX");
        responseDTO.setValor(150.0);
        responseDTO.setDthora(requestDTO.getDthora());
        responseDTO.setStatus(StatusTransacao.FALHA);
        responseDTO.setClienteDestinatarioId(2L);
        responseDTO.setClienteRemetenteId(1L);
        responseDTO.setCartaoId(3L);

        when(service.salvar(any(TransacaoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FALHA"));
    }

    // TESTES ENDPOINT - GET(Listar Todos): Cenario Postivo
    @Test
    void deveRetornarListaDeTransacoesQuandoExistiremTransacoes() throws Exception {
        TransacaoResponseDTO dto1 = new TransacaoResponseDTO();
        dto1.setTipoTransacao("DEBITO");
        dto1.setValor(100.0);
        dto1.setStatus(StatusTransacao.SUCESSO);

        TransacaoResponseDTO dto2 = new TransacaoResponseDTO();
        dto2.setTipoTransacao("CREDITO");
        dto2.setValor(50.0);
        dto2.setStatus(StatusTransacao.SUCESSO);

        List<TransacaoResponseDTO> lista = Arrays.asList(dto1, dto2);

        when(service.listarTodasTransacoes()).thenReturn(lista);

        mockMvc.perform(get("/transacao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].tipoTransacao").value("DEBITO"))
                .andExpect(jsonPath("$[1].tipoTransacao").value("CREDITO"));
    }

    // TESTES ENDPOINT - GET(Listar Todos): Cenario Negativo
    @Test
    void deveRetornar500QuandoOcorreErroInternoAoListarTransacoes() throws Exception {
        when(service.listarTodasTransacoes()).thenThrow(new RuntimeException("Erro interno"));

        mockMvc.perform(get("/transacao"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Erro interno")));
    }

    // TESTES ENDPOINT - GET(Por ID): Cenario Positivo
    @Test
    void deveRetornarTransacaoQuandoIdExistente() throws Exception {
        Long id = 1L;
        TransacaoResponseDTO dto = new TransacaoResponseDTO();
        dto.setValor(100.0);
        dto.setTipoTransacao("DEBITO");

        when(service.listarTransacaoPorId(id)).thenReturn(dto);

        mockMvc.perform(get("/transacao/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.valor").value(100.0))
                .andExpect(jsonPath("$.tipoTransacao").value("DEBITO"));
    }


    // TESTES ENDPOINT - GET(Por ID): Cenario Negativo
    @Test
    void deveRetornar500QuandoIdInvalido() throws Exception {
        Long idInvalido = -1L;

        when(service.listarTransacaoPorId(idInvalido)).thenThrow(new RuntimeException("ID inválido."));

        mockMvc.perform(get("/transacao/{id}", idInvalido))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("ID inválido")));
    }


}