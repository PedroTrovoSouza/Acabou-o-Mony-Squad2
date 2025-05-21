package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.mony.dto.PedidoRequestDTO;
import com.acabou_o_mony.mony.dto.PedidoResponseDTO;
import com.acabou_o_mony.mony.entity.Cartao;
import com.acabou_o_mony.mony.entity.Pedido;
import com.acabou_o_mony.mony.entity.Produto;
import com.acabou_o_mony.mony.mapper.MapperPedido;
import com.acabou_o_mony.mony.repository.CartaoRepository;
import com.acabou_o_mony.mony.repository.PedidoRepository;
import com.acabou_o_mony.mony.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    MapperPedido mapperPedido;

    public PedidoResponseDTO buscarPedido(long id){
        PedidoResponseDTO pedido = pedidoRepository.buscarPedidoComNomeDoProduto(id);

        return pedido;
    }

    public PedidoCartaoProdutoDTO cadastrarPedido(PedidoCartaoProdutoDTO dto) {
        System.out.println(" " + dto.getNomeProduto()+ " "+ dto.getNumeroCartao()+ " "+ " "+ " " +dto.getStatus()+ " "+dto.getValorTotal());
        if (dto == null) {
            throw new RuntimeException("Pedido não pode ser nulo");
        }

        var cartao = cartaoRepository.findByNumero(dto.getNumeroCartao());
        var produto = produtoRepository.findByNome(dto.getNomeProduto());

        if (cartao.isEmpty() || produto.isEmpty()) {
            throw new RuntimeException("Cartão ou Produto não encontrado");
        }

        Pedido pedido = mapperPedido.toEntityProduto(dto);
        pedido.setCartao(cartao.get());
        pedido.setProduto(produto.get());

        Pedido salvo = pedidoRepository.save(pedido);

        return mapperPedido.toPedidoCartaoProdutoDTO(salvo);
    }

}

}
