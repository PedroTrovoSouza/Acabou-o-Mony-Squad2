package com.acabou_o_mony.mony.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequestDTO(
    @NotBlank(message = "O nome do produto não pode ser vazio")
    String nome,

    @NotNull(message = "A categoria do produto não pode ser nula")
    String categoria,

    @NotNull(message = "O preço do produto não pode ser nulo")
    Double preco,

    @NotBlank(message = "A quantidade em estoque não pode ser vazia")
    String qtd_estoque
) { }
