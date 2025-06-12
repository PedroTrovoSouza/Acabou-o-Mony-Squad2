package com.example.transacao_service.dto.transacao;

import com.example.transacao_service.enums.StatusTransacao;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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