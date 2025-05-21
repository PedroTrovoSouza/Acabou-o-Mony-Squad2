package com.acabou_o_mony.mony.dto.cliente;

import java.time.LocalDate;

public record JuridicaResponseDto(
        String nome,
        String cnpj,
        LocalDate dataFundacao
) {
}
