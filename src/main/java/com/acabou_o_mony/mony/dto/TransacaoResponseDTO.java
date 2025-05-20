package com.acabou_o_mony.mony.dto;

import com.acabou_o_mony.mony.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransacaoResponseDTO {
    private Long id;
    private String tipoTransacao;
    private Double valor;
    private LocalDateTime dthora;
    private int destinatario;
    private Status status;
    private Long remetenteId;
    private CartaoResponseDTO cartao;
    private Long contaId;
}
