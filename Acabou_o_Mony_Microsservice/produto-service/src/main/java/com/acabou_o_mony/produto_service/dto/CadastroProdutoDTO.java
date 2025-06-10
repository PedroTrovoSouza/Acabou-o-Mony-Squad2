package com.acabou_o_mony.produto_service.dto;


public record CadastroProdutoDTO(
        String nome,
        String categoria,
        double preco,
        int qtd_estoque
) {
}
