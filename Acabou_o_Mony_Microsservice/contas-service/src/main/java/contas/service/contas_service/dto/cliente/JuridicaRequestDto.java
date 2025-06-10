package contas.service.contas_service.dto.cliente;

import contas.service.contas_service.enums.PerfilEconomico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record JuridicaRequestDto(
        @NotBlank String nome,
        @NotBlank  @Size(max = 14) String cnpj,
        @NotBlank String senha,
        @NotNull PerfilEconomico perfilEconomico,
        @NotBlank String email,
        @DateTimeFormat LocalDate dataFundacao
) {
}
