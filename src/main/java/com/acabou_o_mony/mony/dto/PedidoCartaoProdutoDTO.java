package com.acabou_o_mony.mony.dto;

import com.acabou_o_mony.mony.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCartaoProdutoDTO {
    double valorTotal;
    String numeroCartao;
    String nomeProduto;
    StatusPedido status = StatusPedido.FINALIZADO;
}
