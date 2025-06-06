package com.acabou_o_mony.pedido.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuscarEmailPedidoDTO {
    private String email;
    private PedidoRequestDTO pedido;
    private String cartao;
    private Long clienteDestinatarioId;
    private Long clienteRemetenteId;
}

