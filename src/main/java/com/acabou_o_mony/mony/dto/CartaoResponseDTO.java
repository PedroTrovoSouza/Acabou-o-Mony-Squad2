package com.acabou_o_mony.mony.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CartaoResponseDTO {
    private String nome;
    private String numero;
    private LocalDate vencimento;
    private String bandeira;
    private boolean isCredito;
    private boolean isDebito;
    private BigDecimal limiteCredito;
    private Long contaId;
}
