package com.example.transacao_service.dto.transacao;

import com.example.transacao_service.enums.StatusTransacao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransacaoResponseDTO {
    private String tipoTransacao;
    private Double valor;
    private LocalDateTime dthora;
    private Long clienteDestinatarioId;
    private StatusTransacao status;
    private Long clienteRemetenteId;
    private Long cartaoId;
}
