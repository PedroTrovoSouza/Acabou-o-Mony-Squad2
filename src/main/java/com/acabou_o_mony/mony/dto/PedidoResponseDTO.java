package com.acabou_o_mony.mony.dto;

import com.acabou_o_mony.mony.entity.Produto;
import com.acabou_o_mony.mony.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
    private double valorTotal;
    private Date dataPedido;
    private Produto produto;
    private StatusPedido statusPedido;
}
