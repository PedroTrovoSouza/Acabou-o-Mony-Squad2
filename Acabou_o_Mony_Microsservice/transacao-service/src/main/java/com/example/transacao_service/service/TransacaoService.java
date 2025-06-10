package com.example.transacao_service.service;

import com.example.transacao_service.dto.conta.ContaDTO;
import com.example.transacao_service.dto.transacao.TransacaoRequestDTO;
import com.example.transacao_service.dto.transacao.TransacaoResponseDTO;
import com.example.transacao_service.entity.Transacao;
import com.example.transacao_service.enums.StatusTransacao;
import com.example.transacao_service.repository.TransacaoRepository;
import com.example.transacao_service.mapper.TransacaoMapper;  // Import do mapper
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransacaoService {

    protected final TransacaoRepository repository;
    private final WebClient webClient;
    protected final TransacaoMapper mapper;

    protected void validarTransacao(TransacaoRequestDTO transacao) {
        if (transacao == null || transacao.getCartaoId() == null) {
            throw new RuntimeException("Transação ou Cartão inválido.");
        }

        if (!"CREDITO".equalsIgnoreCase(transacao.getTipoTransacao()) &&
                !"DEBITO".equalsIgnoreCase(transacao.getTipoTransacao())) {
            throw new IllegalArgumentException("Tipo de transação inválido");
        }
    }

    protected void atualizarSaldo(Long clienteId, BigDecimal valor) {
        webClient.put()
                .uri("http://localhost:9092/contas/saldo/{id}", clienteId)
                .bodyValue(valor.doubleValue())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    protected BigDecimal buscarSaldo(Long clienteId) {
        ContaDTO conta = webClient.get()
                .uri("http://localhost:9092/contas/{id}", clienteId)
                .retrieve()
                .bodyToMono(ContaDTO.class)
                .block();

        if (conta == null || conta.getSaldo() == null) {
            throw new RuntimeException("Conta não encontrada ou saldo inválido.");
        }

        return conta.getSaldo();
    }

    public TransacaoResponseDTO salvar(TransacaoRequestDTO dto) {
        validarTransacao(dto);

        Long clienteRemetenteId = dto.getClienteRemetenteId();
        Long clienteDestinatarioId = dto.getClienteDestinatarioId();

        BigDecimal saldo = buscarSaldo(clienteRemetenteId);
        BigDecimal valorTransacao = BigDecimal.valueOf(dto.getValor());

        String tipo = dto.getTipoTransacao();
        if (!"DEBITO".equalsIgnoreCase(tipo) && !"CREDITO".equalsIgnoreCase(tipo)) {
            dto.setStatus(StatusTransacao.FALHA);
        } else {
            if (valorTransacao.compareTo(BigDecimal.ZERO) <= 0 || valorTransacao.compareTo(saldo) > 0) {
                dto.setStatus(StatusTransacao.FALHA);
            } else {
                dto.setStatus(StatusTransacao.SUCESSO);
                atualizarSaldo(clienteDestinatarioId, valorTransacao);
                atualizarSaldo(clienteRemetenteId, valorTransacao.negate());
            }
        }

        Transacao entity = mapper.toEntity(dto);
        Transacao saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }


    public List<TransacaoResponseDTO> listarTodasTransacoes() {
        List<Transacao> transacoes = repository.findAll();

        return transacoes.stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TransacaoResponseDTO listarTransacaoPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("ID inválido.");
        }

        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacao não encontrado."));

        return mapper.toResponseDTO(transacao);
    }
}