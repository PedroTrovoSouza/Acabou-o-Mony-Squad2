package contas.service.contas_service.dto.conta;

import contas.service.contas_service.enums.TipoConta;

import java.math.BigDecimal;

public record ContaResponseDto(
        String agencia,
        TipoConta tipoConta,
        BigDecimal saldo,
        Boolean isAtiva,
        Long idCliente
) {
}
