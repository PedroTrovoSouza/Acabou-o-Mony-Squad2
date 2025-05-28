package com.example.transacao_service.dto.conta;

import com.example.transacao_service.enums.TipoConta;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContaDTO {
    private String agencia;
    private TipoConta tipoConta;
    private BigDecimal saldo;
    private Boolean isAtiva;
    private Long idCliente;
}
