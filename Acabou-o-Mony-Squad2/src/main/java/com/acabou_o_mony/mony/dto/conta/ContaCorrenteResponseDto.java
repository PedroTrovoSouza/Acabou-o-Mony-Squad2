package com.acabou_o_mony.mony.dto.conta;

import com.acabou_o_mony.mony.enums.TipoConta;

import java.math.BigDecimal;

public record ContaCorrenteResponseDto(
        String agencia,
        TipoConta tipoConta,
        BigDecimal saldo,
        BigDecimal limite,
        Boolean isAtiva,
        Long idCliente
) {
}
