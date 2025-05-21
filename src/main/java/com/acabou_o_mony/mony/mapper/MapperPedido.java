package com.acabou_o_mony.mony.mapper;

import com.acabou_o_mony.mony.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.mony.dto.PedidoRequestDTO;
import com.acabou_o_mony.mony.dto.PedidoResponseDTO;
import com.acabou_o_mony.mony.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperPedido {

    Pedido toEntity(PedidoRequestDTO pedidoRequestDTO);

    PedidoRequestDTO toPedidoRequestDTO(Pedido entity);

    PedidoResponseDTO toPedidoResponseDTO(Pedido entity);


    @Mapping(target = "dataPedido", expression = "java(new java.util.Date())")
    @Mapping(target = "cartao", ignore = true)
    @Mapping(target = "produto", ignore = true)
    Pedido toEntityProduto(PedidoCartaoProdutoDTO dto);

    PedidoCartaoProdutoDTO toPedidoCartaoProdutoDTO(Pedido entity);
}

