package com.acabou_o_mony.mony.dto.cliente;

import com.acabou_o_mony.mony.enums.Genero;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record FisicaAtualizacaoDto(
        String nome,
        LocalDate dataNascimento,
        Genero genero
) {
}
