package com.acabou_o_mony.mony.entity;

import com.acabou_o_mony.mony.enums.StatusTransacao;
import com.acabou_o_mony.mony.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TipoTransacao tipo_transacao;
    private Double valor;
    private LocalDateTime dthora;
    private StatusTransacao status;
    private Long clienteDestinatarioId;
    private Long clienteRemetenteId;
    private Long cartaoId;
}
