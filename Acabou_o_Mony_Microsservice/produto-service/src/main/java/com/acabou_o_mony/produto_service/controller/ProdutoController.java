package com.acabou_o_mony.produto_service.controller;


import com.acabou_o_mony.produto_service.dto.*;
import com.acabou_o_mony.produto_service.entity.Produto;
import com.acabou_o_mony.produto_service.mapper.MapperProduto;
import com.acabou_o_mony.produto_service.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    @Autowired
    private MapperProduto mapperProduto;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<String> cadastrarProduto(@RequestBody @Valid CadastroProdutoDTO produtodto) {
        try {
            produtoService.saveProduto(produtodto);
            return ResponseEntity.status(201).body("Cadastro de produto feito com sucesso!");
        } catch (RuntimeException e) {
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
            List<Produto> produtos = produtoService.listAll();
//            List<ListagemProdutoDTO> response = produtos.stream()
//                    .map(mapperProduto::toListagemProdutoDTO)
//                    .toList();

            return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListagemProdutoDTO> listarUmProduto(@PathVariable Long id) {
        try {
            Produto produto = produtoService.listarUmProduto(id);
            ListagemProdutoDTO dto = mapperProduto.toListagemProdutoDTO(produto);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome")
    public ResponseEntity<List<ListagemProdutoDTO>> buscarPorNome(@RequestParam String nome) {
        var produtos = produtoService.buscarPorNome(nome);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        var dtos = produtos.stream()
                .map(mapperProduto::toListagemProdutoDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PatchMapping("/{id}/nome")
    public ResponseEntity<ListagemProdutoDTO> atualizarNome(@PathVariable Long id, @RequestBody @Valid AtualizarNomeProdutoDTO nomeDTO) {
        try {
            var produtoAtualizado = produtoService.atualizarNome(id, nomeDTO.nome());
            var dto = mapperProduto.toListagemProdutoDTO(produtoAtualizado);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/categoria")
    public ResponseEntity<ListagemProdutoDTO> atualizarCategoria(@PathVariable Long id, @RequestBody @Valid AtualizarCategoriaProdutoDTO categoriaDTO) {
        try {
            var produtoAtualizado = produtoService.atualizarCategoria(id, categoriaDTO.categoria());
            var dto = mapperProduto.toListagemProdutoDTO(produtoAtualizado);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/preco")
    public ResponseEntity<ListagemProdutoDTO> atualizarPreco(@PathVariable Long id, @RequestBody @Valid AtualizarPrecoProdutoDTO precoDTO) {
        try {
            var produtoAtualizado = produtoService.atualizarPreco(id, precoDTO.preco());
            var dto = mapperProduto.toListagemProdutoDTO(produtoAtualizado);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<ListagemProdutoDTO> atualizarEstoque(@PathVariable Long id, @RequestBody @Valid AtualizarEstoqueProdutoDTO estoqueDTO) {
        try {
            var produtoAtualizado = produtoService.atualizarEstoque(id, estoqueDTO.qtd_estoque());
            var dto = mapperProduto.toListagemProdutoDTO(produtoAtualizado);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
