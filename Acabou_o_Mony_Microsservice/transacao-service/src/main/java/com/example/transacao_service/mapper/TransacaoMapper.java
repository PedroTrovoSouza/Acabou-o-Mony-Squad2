package com.example.transacao_service.mapper;

import com.example.transacao_service.dto.transacao.TransacaoRequestDTO;
import com.example.transacao_service.dto.transacao.TransacaoResponseDTO;
import com.example.transacao_service.entity.Transacao;
import com.example.transacao_service.enums.TipoTransacao;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransacaoMapper {

    public Transacao toEntity(TransacaoRequestDTO dto) {
        if (dto == null) return null;

        Transacao transacao = new Transacao();
        transacao.setTipo_transacao(TipoTransacao.valueOf(dto.getTipoTransacao()));
        transacao.setValor(dto.getValor());
        transacao.setDthora(LocalDateTime.now());
        transacao.setStatus(dto.getStatus());
        transacao.setClienteDestinatarioId(dto.getClienteDestinatarioId());
        transacao.setClienteRemetenteId(dto.getClienteRemetenteId());
        transacao.setCartaoId(dto.getCartaoId());
        return transacao;
    }

    public TransacaoResponseDTO toResponseDTO(Transacao transacao) {
        if (transacao == null) return null;

        TransacaoResponseDTO dto = new TransacaoResponseDTO();
        dto.setTipoTransacao(transacao.getTipo_transacao().name());
        dto.setValor(transacao.getValor());
        dto.setDthora(transacao.getDthora());
        dto.setStatus(transacao.getStatus());
        dto.setClienteDestinatarioId(transacao.getClienteDestinatarioId());
        dto.setClienteRemetenteId(transacao.getClienteRemetenteId());
        dto.setCartaoId(transacao.getCartaoId());

        return dto;
    }
}