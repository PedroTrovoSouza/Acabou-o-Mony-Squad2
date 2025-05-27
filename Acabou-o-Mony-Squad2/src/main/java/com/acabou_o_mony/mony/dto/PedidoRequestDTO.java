package com.acabou_o_mony.mony.dto;

import com.acabou_o_mony.mony.entity.Cartao;
import com.acabou_o_mony.mony.entity.Produto;
import com.acabou_o_mony.mony.enums.StatusPedido;

import java.util.Date;

public class PedidoRequestDTO {
    private double valorTotal;
    private Date dataPedido;
    private Produto produto;
    private Cartao cartao;
    private StatusPedido statusPedido;
}
