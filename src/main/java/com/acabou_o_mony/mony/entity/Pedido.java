package com.acabou_o_mony.mony.entity;

import com.acabou_o_mony.mony.enums.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    private double valorTotal;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;
}
