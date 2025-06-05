package com.acabou_o_mony.pedido.controller;

import com.acabou_o_mony.pedido.dto.BuscarEmailPedidoDTO;
import com.acabou_o_mony.pedido.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.pedido.dto.PedidoRequestDTO;
import com.acabou_o_mony.pedido.dto.PedidoResponseDTO;
import com.acabou_o_mony.pedido.entity.Pedido;
import com.acabou_o_mony.pedido.enums.StatusPedido;
import com.acabou_o_mony.pedido.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    @Test
    void postPedido_quandoValido_deveRetornarCreatedComBody() throws Exception {
        String nomeProduto = "Produto Teste";

        PedidoRequestDTO pedidoRequest = new PedidoRequestDTO(
                100.0,
                new Date(),
                1L,
                1L,
                StatusPedido.APROVADO
        );

        BuscarEmailPedidoDTO buscarEmailPedidoDTO = new BuscarEmailPedidoDTO();
        buscarEmailPedidoDTO.setEmail("teste@teste.com");
        buscarEmailPedidoDTO.setPedido(pedidoRequest);
        buscarEmailPedidoDTO.setCartao("1234");
        buscarEmailPedidoDTO.setClienteDestinatarioId(1L);
        buscarEmailPedidoDTO.setClienteRemetenteId(2L);

        PedidoCartaoProdutoDTO pedidoCriado = new PedidoCartaoProdutoDTO();

        when(pedidoService.cadastrarPedido(eq(nomeProduto), any(BuscarEmailPedidoDTO.class)))
                .thenReturn(pedidoCriado);

        mockMvc.perform(post("/pedido/cadastrar")
                        .param("nomeProduto", nomeProduto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(buscarEmailPedidoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void postPedido_quandoServiceLancaException_deveRetornarBadRequest() throws Exception {
        String nomeProduto = "Produto Teste";

        BuscarEmailPedidoDTO buscarEmailPedidoDTO = new BuscarEmailPedidoDTO();
        buscarEmailPedidoDTO.setEmail("teste@teste.com");

        when(pedidoService.cadastrarPedido(eq(nomeProduto), any(BuscarEmailPedidoDTO.class)))
                .thenThrow(new RuntimeException("Erro ao cadastrar"));

        mockMvc.perform(post("/pedido/cadastrar")
                        .param("nomeProduto", nomeProduto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(buscarEmailPedidoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Erro ao cadastrar"));
    }

    @Test
    void getPedido_quandoPedidoExiste_deveRetornarOkComBody() throws Exception {
        long pedidoId = 1L;

        PedidoResponseDTO responseDTO = new PedidoResponseDTO(
                "Produto Teste",
                100.0,
                new Date(),
                StatusPedido.APROVADO
        );

        when(pedidoService.buscarPedido(pedidoId)).thenReturn(responseDTO);

        mockMvc.perform(get("/pedido/{id}", pedidoId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeProduto").value("Produto Teste"))
                .andExpect(jsonPath("$.valorTotal").value(100.0))
                .andExpect(jsonPath("$.status").value("APROVADO"));
    }

    @Test
    void getPedido_quandoPedidoNaoExiste_deveRetornarNotFound() throws Exception {
        long pedidoId = 99L;

        when(pedidoService.buscarPedido(pedidoId))
                .thenThrow(new RuntimeException("Pedido com ID " + pedidoId + " não encontrado."));

        mockMvc.perform(get("/pedido/{id}", pedidoId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pedido com ID " + pedidoId + " não encontrado."));
    }

    @Test
    void deletePedido_quandoPedidoExiste_deveRetornarNoContent() throws Exception {
        long pedidoId = 1L;
        Pedido pedido = new Pedido();
        pedido.setIdPedido(pedidoId);

        when(pedidoService.deletePedido(pedidoId)).thenReturn(Optional.of(pedido));

        mockMvc.perform(delete("/pedido/cancelar/{id}", pedidoId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePedido_quandoPedidoNaoExiste_deveRetornarNotFound() throws Exception {
        long pedidoId = 2L;

        doThrow(new RuntimeException("Pedido não encontrado")).when(pedidoService).deletePedido(pedidoId);

        mockMvc.perform(delete("/pedido/cancelar/{id}", pedidoId))
                .andExpect(status().isNotFound());
    }
}
