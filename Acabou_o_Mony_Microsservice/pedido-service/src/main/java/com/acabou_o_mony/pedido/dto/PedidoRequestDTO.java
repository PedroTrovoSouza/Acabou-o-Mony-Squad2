package com.acabou_o_mony.pedido.dto;

import com.acabou_o_mony.pedido.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PedidoRequestDTO {
    private double valorTotal;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPedido;

    private Long cartao;
    private StatusPedido status;

    public PedidoRequestDTO(double valorTotal, Date dataPedido, Long cartao, StatusPedido status){
        this.valorTotal = valorTotal;
        this.dataPedido = dataPedido;
        this.cartao = cartao;
        this.status = status;
    }

    public PedidoRequestDTO(){

    }
}
