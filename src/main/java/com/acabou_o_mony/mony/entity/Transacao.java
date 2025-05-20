package com.acabou_o_mony.mony.entity;

import com.acabou_o_mony.mony.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // vai ser enum
    private String tipo_transacao;
    private Double valor;
    private LocalDateTime dthora;
    private int destinatario;
    private Enum<Status> status;

    @OneToOne
    private Cliente remetente;

    @OneToOne
    private Cartao cartao;

    @OneToOne
    private Conta conta;
}
