package com.acabou_o_mony.mony.controller;

import com.acabou_o_mony.mony.dto.PedidoCartaoProdutoDTO;
import com.acabou_o_mony.mony.dto.PedidoRequestDTO;
import com.acabou_o_mony.mony.entity.Pedido;
import com.acabou_o_mony.mony.repository.ProdutoRepository;
import com.acabou_o_mony.mony.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPedido(@PathVariable long id){
        try {
            return ResponseEntity.status(200).body(pedidoService.buscarPedido(id));
        }catch (RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> postPedido(@RequestBody PedidoCartaoProdutoDTO novoPedido) {
        try {
            PedidoCartaoProdutoDTO pedidoCriado = pedidoService.cadastrarPedido(novoPedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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
