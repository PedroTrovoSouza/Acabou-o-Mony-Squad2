package com.acabou_o_mony.mony.controller;

import com.acabou_o_mony.mony.entity.Produto;
import com.acabou_o_mony.mony.enums.CategoriaProduto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCadastrarProdutoComSucesso() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setCategoria(CategoriaProduto.fromString("Eletrônicos")); // Convertendo uma string para a enum correta // Substitua "TESTE" por um valor válido da sua enum
        produto.setPreco(100.0);
        produto.setQtd_estoque(10);

        mockMvc.perform(post("/produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Cadastro de produto feito com sucesso!"));
    }

    @Test
    public void deveListarTodosProdutos() throws Exception {
        mockMvc.perform(get("/produto"))
                .andExpect(status().isOk());
    }

    @Test
    public void deveExcluirProdutoPorId() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/produto/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deveAtualizarNomeProduto() throws Exception {
        Long id = 1L;
        Produto produto = new Produto();
        produto.setNome("Novo Nome");

        mockMvc.perform(patch("/produto/" + id + "/nome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(produto)))
                .andExpect(status().isOk());
    }
}