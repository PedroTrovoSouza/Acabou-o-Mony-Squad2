package com.acabou_o_mony.mony.entity;

import com.acabou_o_mony.mony.enums.TipoConta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String agencia;

    private TipoConta tipoConta;

    private BigDecimal saldo;

    private Boolean isDebito;

    private Boolean isCredito;

    private BigDecimal limiteCredito;

    private Boolean isAtiva;
}
