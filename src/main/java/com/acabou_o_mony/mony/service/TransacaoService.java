package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.entity.Transacao;
import com.acabou_o_mony.mony.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    public Transacao salvar(Transacao transacao) {
        if (transacao == null) {
            throw new RuntimeException("Impossível seguir com cadastro, dados inválidos");
        }

        Transacao transacaoSalva = repository.save(transacao);

        return transacaoSalva;
    }

    public List<Transacao> listarTodasTransacoes() {
        List<Transacao> transacoes = repository.findAll();

        return transacoes.stream()
                .toList();
    }

    public Transacao listarTransacaoPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("ID inválido.");
        }

        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacao não encontrado."));

        return transacao;
    }
}
