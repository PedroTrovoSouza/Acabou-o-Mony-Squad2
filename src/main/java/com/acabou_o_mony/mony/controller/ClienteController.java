package com.acabou_o_mony.mony.controller;

import com.acabou_o_mony.mony.dto.cliente.ClienteAtualizacaoDto;
import com.acabou_o_mony.mony.dto.cliente.ClienteRequestDto;
import com.acabou_o_mony.mony.dto.cliente.ClienteResponseDto;
import com.acabou_o_mony.mony.dto.mapper.ClienteMapper;
import com.acabou_o_mony.mony.entity.Cliente;
import com.acabou_o_mony.mony.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listarClientes(){
        List<Cliente> clientes = clienteService.listarClientes();
        List<ClienteResponseDto> response = clientes.stream().map(ClienteMapper::toResponseDto).toList();
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> cadastrarCliente(ClienteRequestDto dtoCadastro){
        Cliente cliente = ClienteMapper.toEntity(dtoCadastro);
        Cliente clienteCadastrado = clienteService.cadastrarCliente(cliente);
        ClienteResponseDto response = ClienteMapper.toResponseDto(clienteCadastrado);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> atualizarCliente(@PathVariable Long id, ClienteAtualizacaoDto dtoAtualizacao){
        Cliente cliente = ClienteMapper.toEntity(dtoAtualizacao);
        Cliente clienteAtualizado = clienteService.atualizarCliente(id, cliente);
        ClienteResponseDto response = ClienteMapper.toResponseDto(clienteAtualizado);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
        return ResponseEntity.ok().build();
    }

}
