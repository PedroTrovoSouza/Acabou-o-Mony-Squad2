package com.acabou_o_mony.mony.mapper;

import com.acabou_o_mony.mony.dto.PedidoRequestDTO;
import com.acabou_o_mony.mony.dto.PedidoResponseDTO;
import com.acabou_o_mony.mony.entity.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperPedido {
    Pedido toEntity(PedidoRequestDTO pedidoRequestDTO);
    PedidoRequestDTO toPedidoRequestDTO(Pedido entity);
    PedidoResponseDTO toPedidoResponseDTO(Pedido entity);
}

}
