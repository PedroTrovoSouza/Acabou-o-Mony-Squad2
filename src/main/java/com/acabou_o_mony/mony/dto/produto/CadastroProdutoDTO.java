package com.acabou_o_mony.mony.dto.produto;


public record CadastroProdutoDTO(
        String nome,
        String categoria,
        double preco,
        int qtd_estoque
) {
}
