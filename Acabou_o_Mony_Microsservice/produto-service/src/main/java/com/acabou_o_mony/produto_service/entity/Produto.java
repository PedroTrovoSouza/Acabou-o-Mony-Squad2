package com.acabou_o_mony.produto_service.entity;

import com.acabou_o_mony.produto_service.dto.CadastroProdutoDTO;
import com.acabou_o_mony.produto_service.enums.CategoriaProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    CategoriaProduto categoria;

    private Double preco;

    private int qtd_estoque;

    public Produto(String nome, CategoriaProduto categoria, Double preco, int qtd_estoque) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.qtd_estoque = qtd_estoque;
    }
    public Produto(CadastroProdutoDTO dto) {
        this.nome = dto.nome();
        this.categoria = CategoriaProduto.valueOf(dto.categoria());
        this.preco = dto.preco();
        this.qtd_estoque = dto.qtd_estoque();
    }

    public Produto() {

    }
}