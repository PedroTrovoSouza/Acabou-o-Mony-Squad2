package com.acabou_o_mony.pedido.dto;

import com.acabou_o_mony.pedido.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PedidoCartaoProdutoDTO {
    double valorTotal;
    String numeroCartao;
    String nomeProduto;

    @JsonFormat(pattern = "dd/MM/yyyy")
    Date dataPedido;

    StatusPedido status;

    public PedidoCartaoProdutoDTO(double valorTotal, String nomeProduto, Date dataPedido, StatusPedido status){
        this.valorTotal = valorTotal;
        this.nomeProduto = nomeProduto;
        this.dataPedido = dataPedido;
        this.status = StatusPedido.EM_ANDAMENTO;
    }

    public PedidoCartaoProdutoDTO(){

    }
}
