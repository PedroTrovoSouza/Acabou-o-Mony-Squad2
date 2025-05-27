package com.acabou_o_mony.pedido.dto;

import com.acabou_o_mony.pedido.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PedidoRequestDTO {
    private double valorTotal;
    private Date dataPedido;
    private Long produto;
    private Long cartao;
    private StatusPedido statusPedido;
}
