package com.acabou_o_mony.mony.controller;


import com.acabou_o_mony.mony.dto.produto.*;
import com.acabou_o_mony.mony.entity.Produto;
import com.acabou_o_mony.mony.mapper.MapperProduto;
import com.acabou_o_mony.mony.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

    @Autowired
    private MapperProduto mapperProduto;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<String> cadastrarProduto(@RequestBody @Valid CadastroProdutoDTO produtodto) {
        try{
            produtoService.saveProduto(produtodto);
            return ResponseEntity.status(201).body("Cadastro de produto feito com sucesso!");
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(409).body("Ocorreu um erro ao cadastrar o produto! Já existe um cadastro com esse nome!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        try {
            produtoService.deleteProduto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        var listarProdutos = produtoService.listarTodos();

        if (listarProdutos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(listarProdutos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> listarUmProduto(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(produtoService.listarUmProduto(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/nome")
    public ResponseEntity<Produto> atualizarNome(@PathVariable Long id, @RequestBody @Valid AtualizarNomeProdutoDTO nomeDTO) {
        try {
            var produto = produtoService.listarUmProduto(id);
            produtoService.atualizarNome(id, nomeDTO.nome());
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/categoria")
    public ResponseEntity<Produto> atualizarCategoria(@PathVariable Long id, @RequestBody @Valid AtualizarCategoriaProdutoDTO categoriaDTO) {
        try {
            var produto = produtoService.listarUmProduto(id);
            produtoService.atualizarCategoria(id, categoriaDTO.categoria());
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/preco")
    public ResponseEntity<Produto> atualizarPreco(@PathVariable Long id, @RequestBody @Valid AtualizarPrecoProdutoDTO precoDTO) {
        try {
            var produto = produtoService.listarUmProduto(id);
            produtoService.atualizarPreco(id, precoDTO.preco());
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<Produto> atualizarEstoque(@PathVariable Long id, @RequestBody @Valid AtualizarEstoqueProdutoDTO estoqueDTO) {
        try {
            var produto = produtoService.listarUmProduto(id);
            produtoService.atualizarEstoque(id, estoqueDTO.qtd_estoque());
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
