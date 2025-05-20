package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.dto.PedidoResponseDTO;
import com.acabou_o_mony.mony.entity.Pedido;
import com.acabou_o_mony.mony.mapper.MapperPedido;
import com.acabou_o_mony.mony.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    MapperPedido mapperPedido;

    public PedidoResponseDTO buscarPedido(long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com id: " + id));

        return mapperPedido.toPedidoResponseDTO(pedido);
    }

}
