package com.acabou_o_mony.mony.dto.transacao;

import com.acabou_o_mony.mony.enums.StatusTransacao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransacaoRequestDTO {
    private String tipoTransacao;
    private Double valor;
    private LocalDateTime dthora;
    private StatusTransacao status;
    private Long clienteDestinatarioId;
    private Long clienteRemetenteId;
    private Long cartaoId;
}
