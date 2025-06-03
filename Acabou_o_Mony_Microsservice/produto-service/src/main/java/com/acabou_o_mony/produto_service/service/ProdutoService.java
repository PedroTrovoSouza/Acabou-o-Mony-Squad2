package com.acabou_o_mony.produto_service.service;


import com.acabou_o_mony.produto_service.dto.CadastroProdutoDTO;
import com.acabou_o_mony.produto_service.dto.ListagemProdutoDTO;
import com.acabou_o_mony.produto_service.entity.Produto;
import com.acabou_o_mony.produto_service.enums.CategoriaProduto;
import com.acabou_o_mony.produto_service.mapper.MapperProduto;
import com.acabou_o_mony.produto_service.repository.ProdutoRepository;
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
            throw new RuntimeException("Este Nome já esta cadastrado");
        }
        var produto = new Produto(produtoDTO);
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

    public Produto atualizarNome(Long id, String nome) {
        produtoRepository.atualizarNome(id, nome);
        return produtoRepository.getReferenceById(id);
    }

    public Produto atualizarCategoria(Long id, String categoria) {
         produtoRepository.atualizarCategoria(id, CategoriaProduto.fromString(categoria));
        return produtoRepository.getReferenceById(id);
    }

    public Produto atualizarPreco(Long id, double preco) {
         produtoRepository.atualizarPreco(id, preco);
        return produtoRepository.getReferenceById(id);
    }

    public Produto atualizarEstoque(Long id, int qtd_estoque) {
         produtoRepository.atualizarQtdEstoque(id, qtd_estoque);
         return produtoRepository.getReferenceById(id);
    }

    public Produto listarUmProduto(Long id) {
        var produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new RuntimeException("Produto com ID não encontrado!");
        }

        return produto.get();
    }

    public List<Produto> listAll() {
        var produtos = produtoRepository.findAll();

        produtos.stream()
                .forEach(p -> System.out.println(p.getNome()));
        return produtos;
    }
}
