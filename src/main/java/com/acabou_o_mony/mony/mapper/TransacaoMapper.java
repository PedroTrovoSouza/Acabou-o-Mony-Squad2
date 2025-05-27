package com.acabou_o_mony.mony.mapper;

import com.acabou_o_mony.mony.dto.cartao.CartaoResponseDTO;
import com.acabou_o_mony.mony.dto.transacao.TransacaoRequestDTO;
import com.acabou_o_mony.mony.dto.transacao.TransacaoResponseDTO;
import com.acabou_o_mony.mony.entity.*;
import com.acabou_o_mony.mony.enums.StatusTransacao;
import com.acabou_o_mony.mony.enums.TipoTransacao;
import com.acabou_o_mony.mony.repository.PessoaFisicaRepository;
import com.acabou_o_mony.mony.repository.PessoaJuridicaRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class TransacaoMapper {

    private final PessoaFisicaRepository pessoaFisicaRepository;

    private final PessoaJuridicaRepository pessoaJuridicaRepository;

    public Transacao toEntity(TransacaoRequestDTO dto) {
        Transacao transacao = new Transacao();
        transacao.setTipo_transacao(TipoTransacao.valueOf(dto.getTipoTransacao()));
        transacao.setValor(dto.getValor());
        transacao.setDthora(dto.getDthora());
        transacao.setClienteDestinatarioId(dto.getClienteDestinatarioId());
        transacao.setStatus(dto.getStatus());

        Optional<PessoaFisica> pessoaFisicaPorId = pessoaFisicaRepository.findById(dto.getClienteRemetenteId());
        Optional<PessoaJuridica> pessoaJuridicaPorId = pessoaJuridicaRepository.findById(dto.getClienteRemetenteId());

        if (pessoaFisicaPorId.isPresent()) {
            Cliente remetente = new PessoaFisica();
            remetente.setId(dto.getClienteRemetenteId());
            transacao.setClienteRemetenteId(remetente.getId());
        }
        if (pessoaJuridicaPorId.isPresent()) {
            Cliente remetente = new PessoaJuridica();
            remetente.setId(dto.getClienteRemetenteId());
            transacao.setClienteRemetenteId(remetente.getId());
        }

        Cartao cartao = new Cartao();
        cartao.setId(dto.getCartaoId());
        transacao.setCartaoId(cartao.getId());

        Conta conta = new Conta();
        conta.setId(dto.getClienteRemetenteId());

        return transacao;
    }

    public TransacaoResponseDTO toResponse(Transacao transacao) {
        TransacaoResponseDTO dto = new TransacaoResponseDTO();
        dto.setId(transacao.getId());
        dto.setTipoTransacao(String.valueOf(transacao.getTipo_transacao()));
        dto.setValor(transacao.getValor());
        dto.setDthora(transacao.getDthora());
        dto.setClienteDestinatarioId(transacao.getClienteDestinatarioId());
        dto.setStatus((StatusTransacao) transacao.getStatus());

        if (transacao.getClienteRemetenteId() != null) {
            dto.setCartaoId(transacao.getClienteRemetenteId());
        }

        if (transacao.getCartaoId() != null) {
            Long cartao = transacao.getCartaoId();
            CartaoResponseDTO cartaoDTO = new CartaoResponseDTO();
        }

        return dto;
    }
}
