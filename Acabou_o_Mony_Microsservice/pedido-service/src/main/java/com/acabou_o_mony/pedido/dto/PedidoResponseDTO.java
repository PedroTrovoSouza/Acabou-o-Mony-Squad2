package com.acabou_o_mony.pedido.dto;

import com.acabou_o_mony.pedido.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class PedidoResponseDTO {
    private String nomeProduto;
    private double valorTotal;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPedido;

    private StatusPedido status;

    public PedidoResponseDTO (String nomeProduto, double valorTotal, Date dataPedido, StatusPedido status){
        this.nomeProduto = nomeProduto;
        this.valorTotal = valorTotal;
        this.dataPedido = dataPedido;
        this.status = status;
    }
}
