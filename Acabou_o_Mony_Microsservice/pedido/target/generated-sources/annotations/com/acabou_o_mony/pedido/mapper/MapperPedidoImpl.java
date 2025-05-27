package com.acabou_o_mony.pedido.mapper;

import com.acabou_o_mony.pedido.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.pedido.dto.PedidoRequestDTO;
import com.acabou_o_mony.pedido.dto.PedidoResponseDTO;
import com.acabou_o_mony.pedido.entity.Pedido;
import com.acabou_o_mony.pedido.enums.StatusPedido;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-27T11:39:04-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
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
        pedido.setProduto( pedidoRequestDTO.getProduto() );
        pedido.setCartao( pedidoRequestDTO.getCartao() );

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
        pedidoRequestDTO.setProduto( entity.getProduto() );
        pedidoRequestDTO.setCartao( entity.getCartao() );

        return pedidoRequestDTO;
    }

    @Override
    public PedidoResponseDTO toPedidoResponseDTO(Pedido entity) {
        if ( entity == null ) {
            return null;
        }

        double valorTotal = 0.0d;
        Date dataPedido = null;
        StatusPedido status = null;

        valorTotal = entity.getValorTotal();
        dataPedido = entity.getDataPedido();
        status = entity.getStatus();

        String nomeProduto = null;

        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO( valorTotal, dataPedido, nomeProduto, status );

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

        StatusPedido status = null;
        double valorTotal = 0.0d;
        Date dataPedido = null;

        status = entity.getStatus();
        valorTotal = entity.getValorTotal();
        dataPedido = entity.getDataPedido();

        String numeroCartao = null;
        String nomeProduto = null;

        PedidoCartaoProdutoDTO pedidoCartaoProdutoDTO = new PedidoCartaoProdutoDTO( valorTotal, numeroCartao, nomeProduto, dataPedido, status );

        return pedidoCartaoProdutoDTO;
    }
}
