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
    date = "2025-06-05T10:36:52-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
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
        if ( pedidoRequestDTO.getProduto() != null ) {
            pedido.setProduto( String.valueOf( pedidoRequestDTO.getProduto() ) );
        }
        pedido.setCartao( pedidoRequestDTO.getCartao() );
        pedido.setStatus( pedidoRequestDTO.getStatus() );

        return pedido;
    }

    @Override
    public PedidoRequestDTO toPedidoRequestDTO(Pedido entity) {
        if ( entity == null ) {
            return null;
        }

        double valorTotal = 0.0d;
        Date dataPedido = null;
        Long produto = null;
        Long cartao = null;
        StatusPedido status = null;

        valorTotal = entity.getValorTotal();
        dataPedido = entity.getDataPedido();
        if ( entity.getProduto() != null ) {
            produto = Long.parseLong( entity.getProduto() );
        }
        cartao = entity.getCartao();
        status = entity.getStatus();

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO( valorTotal, dataPedido, produto, cartao, status );

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

        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO( nomeProduto, valorTotal, dataPedido, status );

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

        String nomeProduto = null;
        StatusPedido status = null;
        double valorTotal = 0.0d;
        Date dataPedido = null;

        nomeProduto = entity.getProduto();
        status = entity.getStatus();
        valorTotal = entity.getValorTotal();
        dataPedido = entity.getDataPedido();

        PedidoCartaoProdutoDTO pedidoCartaoProdutoDTO = new PedidoCartaoProdutoDTO( valorTotal, nomeProduto, dataPedido, status );

        if ( entity.getCartao() != null ) {
            pedidoCartaoProdutoDTO.setNumeroCartao( String.valueOf( entity.getCartao() ) );
        }

        return pedidoCartaoProdutoDTO;
    }
}
