package com.acabou_o_mony.mony.service;
import com.acabou_o_mony.mony.dto.produto.CadastroProdutoDTO;
import com.acabou_o_mony.mony.dto.produto.ListagemProdutoDTO;
import com.acabou_o_mony.mony.entity.Produto;
import com.acabou_o_mony.mony.repository.ProdutoRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
@SpringBootTest
public class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @MockitoBean
    private ProdutoRepository produtoRepository;

    @Test
    public void deveSalvarProdutoComSucesso() {
        CadastroProdutoDTO produtoDTO = new CadastroProdutoDTO("Produto Teste", "Categoria Teste", 100.0, 10);
        Produto produto = new Produto(produtoDTO);

        when(produtoRepository.existsByNome(produtoDTO.nome())).thenReturn(false);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ListagemProdutoDTO resultado = produtoService.saveProduto(produtoDTO);

        assertNotNull(resultado);
        assertEquals(produtoDTO.nome(), resultado.nome());
    }

    @Test
    public void deveFalharAoSalvarProdutoDuplicado() {
        CadastroProdutoDTO produtoDTO = new CadastroProdutoDTO("Produto Teste", "Categoria Teste", 100.0, 10);

        when(produtoRepository.existsByNome(produtoDTO.nome())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> produtoService.saveProduto(produtoDTO));
    }

    @Test
    public void deveExcluirProdutoComSucesso() {
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).deleteById(id);

        assertDoesNotThrow(() -> produtoService.deleteProduto(id));
    }

    @Test
    public void deveFalharAoExcluirProdutoInexistente() {
        Long id = 99L;

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> produtoService.deleteProduto(id));
    }

    @Test
    public void deveAtualizarNomeProduto() {
        Long id = 1L;
        String novoNome = "Novo Nome";

        doNothing().when(produtoRepository).atualizarNome(id, novoNome);

        assertDoesNotThrow(() -> produtoService.atualizarNome(id, novoNome));
    }
}