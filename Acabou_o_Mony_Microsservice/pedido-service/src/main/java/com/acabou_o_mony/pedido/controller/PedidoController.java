package com.acabou_o_mony.pedido.controller;

import com.acabou_o_mony.pedido.dto.BuscarEmailPedidoDTO;
import com.acabou_o_mony.pedido.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.pedido.dto.PedidoResponseDTO;
import com.acabou_o_mony.pedido.entity.Pedido;
import com.acabou_o_mony.pedido.service.PedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPedido(@PathVariable long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.buscarPedido(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<PedidoCartaoProdutoDTO> cadastrarPedido(@RequestParam("nomeProduto") String nomeProduto, @RequestBody BuscarEmailPedidoDTO buscarEmailPedidoDTO) throws JsonProcessingException {

        PedidoCartaoProdutoDTO pedidoCriado = pedidoService.cadastrarPedido(nomeProduto, buscarEmailPedidoDTO);
        return ResponseEntity.ok(pedidoCriado);
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable long id){
        try {
            Optional<Pedido> pedidoDelete = pedidoService.deletePedido(id);
            return ResponseEntity.status(204).build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
