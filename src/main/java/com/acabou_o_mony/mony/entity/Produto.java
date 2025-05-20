package com.acabou_o_mony.mony.entity;

import com.acabou_o_mony.mony.enums.Categoria;
import com.acabou_o_mony.mony.enums.Categoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    Categoria categoria;

    private Double preco;

    private String qtd_estoque;





}