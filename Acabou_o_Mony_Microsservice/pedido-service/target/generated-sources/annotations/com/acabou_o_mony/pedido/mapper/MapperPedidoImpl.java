package com.acabou_o_mony.pedido.mapper;

import com.acabou_o_mony.pedido.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.pedido.dto.PedidoRequestDTO;
import com.acabou_o_mony.pedido.dto.PedidoResponseDTO;
import com.acabou_o_mony.pedido.entity.Pedido;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-12T09:29:30-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Amazon.com Inc.)"
)
@Component
public class MapperPedidoImpl implements MapperPedido {

    @Override
    public Pedido toEntity(PedidoRequestDTO pedidoRequestDTO) {
        if ( pedidoRequestDTO == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setValorTotal( pedidoRequestDTO.getValorTotal() );
        pedido.setDataPedido( pedidoRequestDTO.getDataPedido() );
        pedido.setCartao( pedidoRequestDTO.getCartao() );
        pedido.setStatus( pedidoRequestDTO.getStatus() );

        return pedido;
    }

    @Override
    public PedidoRequestDTO toPedidoRequestDTO(Pedido entity) {
        if ( entity == null ) {
            return null;
        }

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();

        pedidoRequestDTO.setValorTotal( entity.getValorTotal() );
        pedidoRequestDTO.setDataPedido( entity.getDataPedido() );
        pedidoRequestDTO.setCartao( entity.getCartao() );
        pedidoRequestDTO.setStatus( entity.getStatus() );

        return pedidoRequestDTO;
    }

    @Override
    public PedidoResponseDTO toPedidoResponseDTO(Pedido entity) {
        if ( entity == null ) {
            return null;
        }

        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();

        pedidoResponseDTO.setValorTotal( entity.getValorTotal() );
        pedidoResponseDTO.setDataPedido( entity.getDataPedido() );
        pedidoResponseDTO.setStatus( entity.getStatus() );

        return pedidoResponseDTO;
    }

    @Override
    public Pedido toEntityProduto(PedidoCartaoProdutoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setStatus( dto.getStatus() );
        pedido.setValorTotal( dto.getValorTotal() );
        pedido.setDataPedido( dto.getDataPedido() );

        return pedido;
    }

    @Override
    public PedidoCartaoProdutoDTO toPedidoCartaoProdutoDTO(Pedido entity) {
        if ( entity == null ) {
            return null;
        }

        PedidoCartaoProdutoDTO pedidoCartaoProdutoDTO = new PedidoCartaoProdutoDTO();

        pedidoCartaoProdutoDTO.setNomeProduto( entity.getProduto() );
        pedidoCartaoProdutoDTO.setStatus( entity.getStatus() );
        if ( entity.getCartao() != null ) {
            pedidoCartaoProdutoDTO.setNumeroCartao( String.valueOf( entity.getCartao() ) );
        }
        pedidoCartaoProdutoDTO.setValorTotal( entity.getValorTotal() );
        pedidoCartaoProdutoDTO.setDataPedido( entity.getDataPedido() );

        return pedidoCartaoProdutoDTO;
    }
}
