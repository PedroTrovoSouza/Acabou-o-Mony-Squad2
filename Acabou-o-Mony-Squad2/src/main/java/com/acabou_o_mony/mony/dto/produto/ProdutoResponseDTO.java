package com.acabou_o_mony.mony.dto.produto;

import com.acabou_o_mony.mony.enums.CategoriaProduto;

public record ProdutoResponseDTO(String nome,
                                 String categoria,
                                 double preco,
                                 int qtd_estoque

) {}
