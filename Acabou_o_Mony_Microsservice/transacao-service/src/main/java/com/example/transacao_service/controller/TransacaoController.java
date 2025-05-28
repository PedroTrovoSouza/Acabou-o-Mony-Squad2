package com.example.transacao_service.controller;

import com.example.transacao_service.dto.transacao.TransacaoRequestDTO;
import com.example.transacao_service.dto.transacao.TransacaoResponseDTO;
import com.example.transacao_service.entity.Transacao;
import com.example.transacao_service.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> cadastrar(@RequestBody TransacaoRequestDTO dto) {
        TransacaoResponseDTO response = service.salvar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodos() {
        List<TransacaoResponseDTO> transacoes = service.listarTodasTransacoes();

        if (transacoes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> listarPorId (@PathVariable Long id) {
        TransacaoResponseDTO transacaoPorId = service.listarTransacaoPorId(id);

        if (transacaoPorId == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(transacaoPorId);
    }
}