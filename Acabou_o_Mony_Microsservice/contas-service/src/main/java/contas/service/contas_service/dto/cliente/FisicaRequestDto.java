package contas.service.contas_service.dto.cliente;

import contas.service.contas_service.enums.Genero;
import contas.service.contas_service.enums.PerfilEconomico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record FisicaRequestDto(
        @NotBlank String nome,
        @NotBlank @Size(max = 12) String cpf,
        @NotNull PerfilEconomico perfilEconomico,
        @NotBlank String email,
        @DateTimeFormat LocalDate dataNascimento,
        Genero genero
) {
}
