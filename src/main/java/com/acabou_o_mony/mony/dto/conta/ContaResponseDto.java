package com.acabou_o_mony.mony.dto.conta;

import com.acabou_o_mony.mony.enums.TipoConta;

import java.math.BigDecimal;

public record ContaResponseDto(
        String agencia,
        TipoConta tipoConta,
        BigDecimal saldo,
        Boolean isAtiva,
        Long idCliente
) {
}
