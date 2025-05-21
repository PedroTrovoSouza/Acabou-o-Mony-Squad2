package com.acabou_o_mony.mony.dto.conta;

import com.acabou_o_mony.mony.enums.TipoConta;

public record ContaRequestDto(
        String agencia,
        TipoConta tipoConta,
        Boolean isDebito,
        Boolean isCredito,
        Long idCliente
) {
}
