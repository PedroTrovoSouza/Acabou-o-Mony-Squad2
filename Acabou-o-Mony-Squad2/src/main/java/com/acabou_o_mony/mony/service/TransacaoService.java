package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.entity.Cartao;
import com.acabou_o_mony.mony.entity.Conta;
import com.acabou_o_mony.mony.entity.Transacao;
import com.acabou_o_mony.mony.enums.StatusTransacao;
import com.acabou_o_mony.mony.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    public Transacao salvar(Transacao transacao) {
        if (transacao == null) {
            throw new RuntimeException("Impossível seguir com cadastro, dados inválidos");
        }

        Long cartaoId = transacao.getCartaoId();

        if (cartaoId == null) {
            throw new RuntimeException("Cartão ou conta não informados corretamente.");
        }

//        Conta conta = cartaoId.getConta();
//        BigDecimal saldoAtual = conta.getSaldo();
//        BigDecimal valorTransacao = BigDecimal.valueOf(transacao.getValor());
//
//        if (valorTransacao.compareTo(BigDecimal.ZERO) <= 0 || valorTransacao.compareTo(saldoAtual) > 0) {
//            transacao.setStatus(StatusTransacao.FALHA);
//        } else {
//            transacao.setStatus(StatusTransacao.SUCESSO);
//            conta.setSaldo(saldoAtual.subtract(valorTransacao));
//        }

        return repository.save(transacao);
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
