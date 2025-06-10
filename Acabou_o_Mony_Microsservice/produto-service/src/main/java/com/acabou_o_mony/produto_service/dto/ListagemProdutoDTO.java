package com.acabou_o_mony.produto_service.dto;

public record ListagemProdutoDTO(
        String nome,
        String categoria,
        double preco,
        int qtd_estoque
) {
}
