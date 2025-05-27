package com.acabou_o_mony.mony.mapper;

import com.acabou_o_mony.mony.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.mony.dto.PedidoRequestDTO;
import com.acabou_o_mony.mony.dto.PedidoResponseDTO;
import com.acabou_o_mony.mony.entity.Pedido;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T14:25:27-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class MapperPedidoImpl implements MapperPedido {

    @Override
    public Pedido toEntity(PedidoRequestDTO pedidoRequestDTO) {
        if ( pedidoRequestDTO == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        return pedido;
    }

    @Override
    public PedidoRequestDTO toPedidoRequestDTO(Pedido entity) {
        if ( entity == null ) {
            return null;
        }

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();

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

        return pedidoResponseDTO;
    }

    @Override
    public Pedido toEntityProduto(PedidoCartaoProdutoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setValorTotal( dto.getValorTotal() );
        pedido.setStatus( dto.getStatus() );

        pedido.setDataPedido( new java.util.Date() );

        return pedido;
    }

    @Override
    public PedidoCartaoProdutoDTO toPedidoCartaoProdutoDTO(Pedido entity) {
        if ( entity == null ) {
            return null;
        }

        PedidoCartaoProdutoDTO pedidoCartaoProdutoDTO = new PedidoCartaoProdutoDTO();

        pedidoCartaoProdutoDTO.setValorTotal( entity.getValorTotal() );
        pedidoCartaoProdutoDTO.setStatus( entity.getStatus() );

        return pedidoCartaoProdutoDTO;
    }
}
