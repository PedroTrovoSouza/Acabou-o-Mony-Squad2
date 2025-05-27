package contas.service.contas_service.dto.conta;

import contas.service.contas_service.enums.TipoCliente;
import contas.service.contas_service.enums.TipoConta;

public record ContaRequestDto(
        String agencia,
        TipoConta tipoConta,
        Boolean isDebito,
        Boolean isCredito,
        Long idCliente,
        TipoCliente tipoCliente
) {
}
