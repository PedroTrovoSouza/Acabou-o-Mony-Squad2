package com.acabou_o_mony.mony.dto.cartao;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class CartaoResponseDTO {
    private String nome;
    private String numero;
    private LocalDate vencimento;
    private String bandeira;
    private Long contaId;
}
