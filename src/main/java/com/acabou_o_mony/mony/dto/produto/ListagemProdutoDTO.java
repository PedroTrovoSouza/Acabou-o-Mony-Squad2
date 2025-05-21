package com.acabou_o_mony.mony.dto.produto;

public record ListagemProdutoDTO(
        String nome,
        String categoria,
        double preco,
        int qtd_estoque
) {
}
