package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.dto.produto.CadastroProdutoDTO;
import com.acabou_o_mony.mony.dto.produto.ListagemProdutoDTO;
import com.acabou_o_mony.mony.entity.Produto;
import com.acabou_o_mony.mony.mapper.MapperProduto;
import com.acabou_o_mony.mony.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MapperProduto mapperProduto;

    @BeforeEach
    void setup() {
        lenient().when(produtoRepository.existsByNome(anyString())).thenReturn(false);
        lenient().when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void deveSalvarProdutoComSucesso() {
        CadastroProdutoDTO produtoDTO = new CadastroProdutoDTO("Produto Teste",
                "ELETRONICOS", 100.0, 10);
        Produto produto = new Produto(produtoDTO);
        ListagemProdutoDTO resultadoEsperado = new ListagemProdutoDTO("Produto Teste", "ELETRONICOS"
        , 100.0, 10);

        when(mapperProduto.toEntity(produtoDTO)).thenReturn(produto);
        when(mapperProduto.toListagemProdutoDTO(produto)).thenReturn(resultadoEsperado);
        when(produtoRepository.existsByNome(produtoDTO.nome())).thenReturn(false);

        when(produtoRepository.save(any())).thenReturn(produto);

        ListagemProdutoDTO resultado = produtoService.saveProduto(produtoDTO);

        assertAll(
                () -> assertNotNull(resultado, "O resultado da operação não deveria ser null"),
                () -> assertEquals(produtoDTO.nome(), resultado.nome(), "O nome do produto deveria ser o mesmo do DTO")
        );
    }

    @Test
    public void deveFalharAoSalvarProdutoDuplicado() {
        CadastroProdutoDTO produtoDTO = new CadastroProdutoDTO("Produto Teste", "Categoria Teste", 100.0, 10);

        when(produtoRepository.existsByNome(produtoDTO.nome())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> produtoService.saveProduto(produtoDTO), "Deveria lançar exceção ao salvar um produto duplicado");
    }

    @Test
    public void deveExcluirProdutoComSucesso() {
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).deleteById(id);

        assertDoesNotThrow(() -> produtoService.deleteProduto(id), "Deveria excluir o produto sem lançar erro");
    }

    @Test
    public void deveFalharAoExcluirProdutoInexistente() {
        Long id = 99L;

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> produtoService.deleteProduto(id), "Deveria lançar erro ao excluir um produto inexistente");
    }

    @Test
    public void deveAtualizarNomeProduto() {
        Long id = 1L;
        String novoNome = "Novo Nome";

        doAnswer(invocation -> null).when(produtoRepository).atualizarNome(id, novoNome);

        assertDoesNotThrow(() -> produtoService.atualizarNome(id, novoNome), "Deveria atualizar o nome sem erro");
    }
}