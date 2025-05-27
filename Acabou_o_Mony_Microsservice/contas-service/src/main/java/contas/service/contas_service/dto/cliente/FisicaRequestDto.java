package contas.service.contas_service.dto.cliente;

import contas.service.contas_service.enums.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record FisicaRequestDto(
        @NotBlank String nome,
        @NotBlank @Size(max = 12) String cpf,
        @DateTimeFormat LocalDate dataNascimento,
        Genero genero
) {
}
