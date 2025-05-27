package com.acabou_o_mony.pedido.dto;

import com.acabou_o_mony.pedido.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PedidoCartaoProdutoDTO {
    double valorTotal;
    String numeroCartao;
    String nomeProduto;
    Date dataPedido;
    StatusPedido status;

    public PedidoCartaoProdutoDTO(double valorTotal, String numeroCartao, String nomeProduto, Date dataPedido, StatusPedido status){
        this.valorTotal = valorTotal;
        this.numeroCartao = numeroCartao;
        this.nomeProduto = nomeProduto;
        this.dataPedido = dataPedido;
        this.status = StatusPedido.FINALIZADO;
    }
}
