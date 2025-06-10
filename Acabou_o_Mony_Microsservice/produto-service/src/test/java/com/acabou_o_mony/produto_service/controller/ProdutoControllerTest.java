//package com.acabou_o_mony.produto_service.controller;
//
//import com.acabou_o_mony.produto_service.entity.Produto;
//import com.acabou_o_mony.produto_service.enums.CategoriaProduto;
//import com.acabou_o_mony.produto_service.repository.ProdutoRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ProdutoControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ProdutoRepository produtoRepository;
//
//    private Long produtoId;
//
//    @BeforeEach
//    @Transactional
//    void setup() {
//        produtoRepository.deleteAll();
//
//        Produto produto = new Produto();
//        produto.setNome("Produto Teste");
//        produto.setCategoria(CategoriaProduto.ELETRONICOS);
//        produto.setPreco(100.0);
//        produto.setQtd_estoque(10);
//
//        Produto savedProduto = produtoRepository.save(produto);
//        produtoRepository.flush();
//
//        produtoId = savedProduto.getId();
//        assertNotNull(produtoId, "Erro: produtoId está NULL após salvar produto!");
//
//        System.out.println("Produto criado com ID: " + produtoId);
//    }
//
//    @Test
//    @Transactional
//    public void deveCadastrarProdutoComSucesso() throws Exception {
//        Produto produto = new Produto();
//        produto.setNome("Novo Produto");
//        produto.setCategoria(CategoriaProduto.ALIMENTOS_BEBIDAS);
//        produto.setPreco(150.0);
//        produto.setQtd_estoque(20);
//
//        mockMvc.perform(post("/produto")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(produto)))
//                .andExpect(status().isCreated())
//                .andExpect(content().string("Cadastro de produto feito com sucesso!"));
//    }
//
//    @Test
//    @Transactional
//    public void deveListarTodosProdutos() throws Exception {
//        List<Produto> produtos = produtoRepository.findAll();
//        System.out.println("Produtos no banco antes do GET: " + produtos.size());
//
//        mockMvc.perform(get("/produto"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(produtos.size())); // Verificar tamanho da lista
//    }
//
//    @Test
//    @Transactional
//    public void deveExcluirProdutoPorId() throws Exception {
//        Produto produto = new Produto();
//        produto.setNome("Produto Teste Excluir");
//        produto.setCategoria(CategoriaProduto.ELETRONICOS);
//        produto.setPreco(99.0);
//        produto.setQtd_estoque(5);
//
//        Produto savedProduto = produtoRepository.save(produto);
//        produtoRepository.flush();
//        Long produtoId = savedProduto.getId();
//
//        System.out.println("Tentando excluir produto com ID: " + produtoId);
//        assertNotNull(produtoId, "Erro: produtoId está NULL antes do DELETE!");
//
//        mockMvc.perform(delete("/produto/" + produtoId))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @Transactional
//    public void deveAtualizarNomeProduto() throws Exception {
//        assertNotNull(produtoId, "Erro: produtoId está NULL antes do PATCH!");
//
//        mockMvc.perform(patch("/produto/" + produtoId + "/nome")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"nome\": \"Novo Nome\"}"))
//                .andExpect(status().isOk());
//    }
//}