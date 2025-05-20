package com.acabou_o_mony.mony.controller;

import com.acabou_o_mony.mony.entity.Transacao;
import com.acabou_o_mony.mony.service.TransacaoService;
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
    public ResponseEntity<Transacao> cadastrar(@RequestBody Transacao dto) {
        Transacao response = service.salvar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listarTodos() {
        List<Transacao> transacoes = service.listarTodasTransacoes();

        if (transacoes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> listarPorId (@PathVariable Long id) {
        Transacao transacaoPorId = service.listarTransacaoPorId(id);

        if (transacaoPorId == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(transacaoPorId);
    }
}
