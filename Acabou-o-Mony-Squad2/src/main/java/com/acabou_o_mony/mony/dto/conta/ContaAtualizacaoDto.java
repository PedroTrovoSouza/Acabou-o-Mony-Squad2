package com.acabou_o_mony.mony.dto.conta;

import com.acabou_o_mony.mony.enums.TipoConta;

public record ContaAtualizacaoDto(
        String agencia,
        TipoConta tipoConta
) {
}
