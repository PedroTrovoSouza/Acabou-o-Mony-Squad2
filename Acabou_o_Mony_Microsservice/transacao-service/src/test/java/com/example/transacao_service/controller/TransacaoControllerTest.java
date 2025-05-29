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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransacaoController.class)
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransacaoService service;

    @Autowired
    private ObjectMapper objectMapper;

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
}