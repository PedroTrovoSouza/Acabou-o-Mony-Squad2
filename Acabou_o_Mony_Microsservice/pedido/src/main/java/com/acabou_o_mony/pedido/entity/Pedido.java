package com.acabou_o_mony.pedido.entity;

import com.acabou_o_mony.pedido.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    private double valorTotal;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPedido;

    private Long produto;

    private Long cartao;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;
}