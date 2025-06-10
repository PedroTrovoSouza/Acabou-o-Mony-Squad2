package com.acabou_o_mony.pedido.dto;

import com.acabou_o_mony.pedido.enums.StatusTransacao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransacaoRequestDTO {
    private String tipoTransacao;
    private Double valor;
    private LocalDateTime dthora;
    private StatusTransacao status;
    private Long clienteDestinatarioId;
    private Long clienteRemetenteId;
    private Long cartaoId;
}
