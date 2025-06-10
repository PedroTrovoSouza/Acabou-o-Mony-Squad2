package com.acabou_o_mony.pedido.dto;

import com.acabou_o_mony.pedido.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuscarEmailPedidoDTO {
    private String login;
    private PedidoRequestDTO pedido;
    private Long clienteDestinatarioId;
    private Long clienteRemetenteId;
}

