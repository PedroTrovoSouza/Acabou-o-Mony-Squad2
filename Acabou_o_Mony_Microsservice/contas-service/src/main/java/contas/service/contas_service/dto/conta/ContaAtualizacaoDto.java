package contas.service.contas_service.dto.conta;


import contas.service.contas_service.enums.TipoConta;

public record ContaAtualizacaoDto(
        String agencia,
        TipoConta tipoConta
) {
}
