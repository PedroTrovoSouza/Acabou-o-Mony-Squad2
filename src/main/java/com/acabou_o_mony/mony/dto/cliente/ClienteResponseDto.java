package com.acabou_o_mony.mony.dto.cliente;

import com.acabou_o_mony.mony.enums.Genero;
import com.acabou_o_mony.mony.enums.TipoRazaoSocial;

import java.time.LocalDate;

public record ClienteResponseDto(String cpfOuCnpj, TipoRazaoSocial razaoSocial, LocalDate dataNascimento,
                                 Genero genero) {
}
