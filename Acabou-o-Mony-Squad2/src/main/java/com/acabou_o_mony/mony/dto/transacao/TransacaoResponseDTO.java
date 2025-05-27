package com.acabou_o_mony.mony.dto.transacao;

import com.acabou_o_mony.mony.enums.StatusTransacao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransacaoResponseDTO {
    private Long id;
    private String tipoTransacao;
    private Double valor;
    private LocalDateTime dthora;
    private Long clienteDestinatarioId;
    private StatusTransacao status;
    private Long clienteRemetenteId;
    private Long cartaoId;
    private Long contaId;
}
