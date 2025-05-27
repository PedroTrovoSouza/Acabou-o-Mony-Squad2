package com.acabou_o_mony.pedido.dto;

import com.acabou_o_mony.pedido.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class PedidoResponseDTO {
    private double valorTotal;
    private Date dataPedido;
    private String nomeProduto;
    private StatusPedido status;

    public PedidoResponseDTO (double valorTotal, Date dataPedido, String nomeProduto, StatusPedido status){
        this.valorTotal = valorTotal;
        this.dataPedido = dataPedido;
        this.nomeProduto = nomeProduto;
        this.status = status;
    }
}
