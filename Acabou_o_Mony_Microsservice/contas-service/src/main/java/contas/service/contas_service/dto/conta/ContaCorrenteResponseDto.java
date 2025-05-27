package contas.service.contas_service.dto.conta;

import contas.service.contas_service.enums.TipoConta;

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
