package com.acabou_o_mony.mony.dto.cliente;

import com.acabou_o_mony.mony.enums.Genero;

import java.time.LocalDate;

public record ClienteAtualizacaoDto(LocalDate dataNascimento, Genero genero) {
}
