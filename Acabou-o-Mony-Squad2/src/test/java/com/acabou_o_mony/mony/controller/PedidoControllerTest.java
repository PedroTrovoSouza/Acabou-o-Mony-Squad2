package com.acabou_o_mony.mony.controller;

import com.acabou_o_mony.mony.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.mony.enums.StatusPedido;
import com.acabou_o_mony.mony.repository.PedidoRepository;
import com.acabou_o_mony.mony.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static com.acabou_o_mony.mony.enums.StatusPedido.FINALIZADO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoRepository pedidoRepository;

    @MockitoBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;


    private PedidoCartaoProdutoDTO criarPedido(String nomeProduto, double valorTotal, String numeroCartao, StatusPedido status) {
        PedidoCartaoProdutoDTO novoPedido = new PedidoCartaoProdutoDTO();
        novoPedido.setNomeProduto(nomeProduto);
        novoPedido.setValorTotal(valorTotal);
        novoPedido.setNumeroCartao(numeroCartao);
        novoPedido.setStatus(status);
        return novoPedido;
    }

    @Test
    void deveCadastrarPedidoComSucesso() throws Exception {
        PedidoCartaoProdutoDTO pedido = new PedidoCartaoProdutoDTO();
        pedido.setNomeProduto("Teclado Mecânico");
        pedido.setNumeroCartao("4111111111111111");
        pedido.setValorTotal(350);
        pedido.setStatus(FINALIZADO);

        when(pedidoService.cadastrarPedido(any(PedidoCartaoProdutoDTO.class)))
                .thenReturn(pedido);

        mockMvc.perform(post("/pedido/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deveListarTodosProdutos() throws Exception {
        Long id = 2L;
        mockMvc.perform(get("/pedido/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void deveExcluirProdutoPorId() throws Exception {
        Long id = 2L;
        mockMvc.perform(delete("/pedido/cancelar/" + id))
                .andExpect(status().isNoContent());
    }

}