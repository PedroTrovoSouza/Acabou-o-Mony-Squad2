package com.example.transacao_service.entity;

import com.example.transacao_service.enums.StatusTransacao;
import com.example.transacao_service.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo_transacao;
    private Double valor;
    private LocalDateTime dthora;
    @Enumerated(EnumType.STRING)
    private StatusTransacao status;
    private Long clienteDestinatarioId;
    private Long clienteRemetenteId;
    private Long cartaoId;
}
