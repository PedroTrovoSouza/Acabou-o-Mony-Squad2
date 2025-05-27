package contas.service.contas_service.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record JuridicaRequestDto(
        @NotBlank String nome,
        @NotBlank  @Size(max = 14) String cnpj,
        @NotBlank String email,
        @DateTimeFormat LocalDate dataFundacao
) {
}
