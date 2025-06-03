package com.acabou_o_mony.pedido.entity;

import com.acabou_o_mony.pedido.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    private double valorTotal;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPedido;

    private String produto;

    private Long cartao;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;
}