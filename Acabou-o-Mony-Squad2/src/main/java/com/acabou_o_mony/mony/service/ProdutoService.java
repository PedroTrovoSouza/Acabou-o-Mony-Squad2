package com.acabou_o_mony.mony.service;


import com.acabou_o_mony.mony.dto.produto.CadastroProdutoDTO;
import com.acabou_o_mony.mony.dto.produto.ListagemProdutoDTO;
import com.acabou_o_mony.mony.entity.Produto;
import com.acabou_o_mony.mony.enums.CategoriaProduto;
import com.acabou_o_mony.mony.mapper.MapperProduto;
import com.acabou_o_mony.mony.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    MapperProduto mapperProduto;

    @Transactional
    public ListagemProdutoDTO saveProduto(CadastroProdutoDTO produtoDTO) {
        if (produtoRepository.existsByNome(produtoDTO.nome())) {
            throw new RuntimeException("Este Nome já está cadastrado");
        }
        var produto = mapperProduto.toEntity(produtoDTO);
        produtoRepository.save(produto);
        return mapperProduto.toListagemProdutoDTO(produto);
    }

    public void deleteProduto(Long id) {
        var produtoEncontrado = produtoRepository.findById(id);

        if (produtoEncontrado.isEmpty()) {
            throw new RuntimeException("Não há produto cadastrado para o id " + id);
        }
        produtoRepository.deleteById(id);
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.buscarPorNome(nome);
    }

    public void atualizarNome(Long id, String nome) {
        produtoRepository.atualizarNome(id, nome);
    }

    public void atualizarCategoria(Long id, String categoria) {
        produtoRepository.atualizarCategoria(id, CategoriaProduto.fromString(categoria));
    }

    public void atualizarPreco(Long id, double preco) {
        produtoRepository.atualizarPreco(id, preco);
    }

    public void atualizarEstoque(Long id, int qtd_estoque) {
        produtoRepository.atualizarQtdEstoque(id, qtd_estoque);
    }

    public Produto listarUmProduto(Long id) {
        var produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new RuntimeException("Produto com ID não encontrado!");
        }

        return produto.get();
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
}
