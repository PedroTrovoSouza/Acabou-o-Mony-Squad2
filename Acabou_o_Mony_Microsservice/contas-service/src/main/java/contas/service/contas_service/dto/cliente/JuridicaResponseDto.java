package contas.service.contas_service.dto.cliente;

import java.time.LocalDate;

public record JuridicaResponseDto(
        String nome,
        String cnpj,
        LocalDate dataFundacao
) {
}
