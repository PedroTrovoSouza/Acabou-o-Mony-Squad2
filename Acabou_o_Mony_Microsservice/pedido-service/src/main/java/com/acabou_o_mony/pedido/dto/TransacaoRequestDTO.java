package com.acabou_o_mony.pedido.dto;

import com.acabou_o_mony.pedido.enums.StatusTransacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequestDTO {
    private String tipoTransacao;
    private Double valor;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private StatusTransacao status;
    private Long clienteDestinatarioId;
    private Long clienteRemetenteId;
    private Long cartaoId;
}
