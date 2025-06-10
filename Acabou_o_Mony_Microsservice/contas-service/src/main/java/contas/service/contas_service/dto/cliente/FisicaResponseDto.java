package contas.service.contas_service.dto.cliente;
import contas.service.contas_service.enums.Genero;

import java.time.LocalDate;

public record FisicaResponseDto(
        String nome,
        String cpf,
        String email,
        LocalDate dataNascimento,
        Genero genero
) {
}
