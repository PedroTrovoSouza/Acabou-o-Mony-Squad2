package com.acabou_o_mony.mony.dto;

import com.acabou_o_mony.mony.enums.StatusTransacao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransacaoRequestDTO {
    private String tipoTransacao;
    private Double valor;
    private LocalDateTime dthora;
    private int destinatario;
    private StatusTransacao status;
    private Long remetenteId;
    private Long cartaoId;
    private Long contaId;
}
