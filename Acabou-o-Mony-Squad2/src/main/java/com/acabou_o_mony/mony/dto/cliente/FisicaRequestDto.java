package com.acabou_o_mony.mony.dto.cliente;

import com.acabou_o_mony.mony.enums.Genero;
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
