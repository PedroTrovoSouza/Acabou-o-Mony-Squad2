package com.acabou_o_mony.pedido.service;

import com.acabou_o_mony.pedido.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.pedido.dto.PedidoRequestDTO;
import com.acabou_o_mony.pedido.entity.Pedido;
import com.acabou_o_mony.pedido.mapper.MapperPedido;
import com.acabou_o_mony.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    MapperPedido mapperPedido;

    public Optional<Pedido> buscarPedido(long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        if (pedido == null) {
            throw new RuntimeException("Pedido não encontrado");
        }

        return pedido;
    }

    public PedidoCartaoProdutoDTO cadastrarPedido(PedidoRequestDTO dto) {
        if (dto == null) {
            throw new RuntimeException("Pedido não pode ser nulo");
        }

        WebClient client = WebClient.create();

        String response = client.get()
                .uri("http://localhost:9090/produto/{id}", dto.getProduto())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Pedido pedido = mapperPedido.toEntity(dto);

        pedido.setDataPedido(new Date());
        pedido.setProduto(dto.getProduto());

        Pedido salvo = pedidoRepository.save(pedido);

        return mapperPedido.toPedidoCartaoProdutoDTO(salvo);
    }

    public Optional<Pedido> deletePedido(long id) {
        Optional<Pedido> pedidoEncontrado = pedidoRepository.findById(id);

        if (pedidoEncontrado == null) {
            throw new RuntimeException("Pedido não encontrado");
        }

        pedidoRepository.delete(pedidoEncontrado.get());
        return pedidoEncontrado;
    }
}