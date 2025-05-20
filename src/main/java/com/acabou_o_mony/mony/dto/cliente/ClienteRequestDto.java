package com.acabou_o_mony.mony.dto.cliente;

import com.acabou_o_mony.mony.enums.Genero;
import com.acabou_o_mony.mony.enums.TipoRazaoSocial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ClienteRequestDto(@NotBlank String cpfOuCnpj, @NotNull TipoRazaoSocial razaoSocial,
                                @NotBlank @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataNascimento,
                                Genero genero) {
}
