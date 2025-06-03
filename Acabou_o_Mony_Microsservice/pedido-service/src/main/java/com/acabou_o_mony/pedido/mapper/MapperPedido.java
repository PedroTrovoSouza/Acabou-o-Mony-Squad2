package com.acabou_o_mony.pedido.mapper;

import com.acabou_o_mony.pedido.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.pedido.dto.PedidoRequestDTO;
import com.acabou_o_mony.pedido.dto.PedidoResponseDTO;
import com.acabou_o_mony.pedido.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperPedido {

    Pedido toEntity(PedidoRequestDTO pedidoRequestDTO);

    PedidoRequestDTO toPedidoRequestDTO(Pedido entity);

    PedidoResponseDTO toPedidoResponseDTO(Pedido entity);

    @Mapping(source = "status", target = "status")
    Pedido toEntityProduto(PedidoCartaoProdutoDTO dto);

    @Mapping(source = "produto", target = "nomeProduto")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "cartao", target = "numeroCartao")
    PedidoCartaoProdutoDTO toPedidoCartaoProdutoDTO(Pedido entity);
}
