package com.acabou_o_mony.mony.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String numero;
    private LocalDate vencimento;
    private String cvv;
    private String bandeira;
    private boolean isCredito;
    private boolean isDebito;
    private BigDecimal limiteCredito;

    @OneToOne
    private Conta conta;
}
