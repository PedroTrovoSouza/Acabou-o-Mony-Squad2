package com.acabou_o_mony.mony.mapper;

import com.acabou_o_mony.mony.dto.CartaoResponseDTO;
import com.acabou_o_mony.mony.dto.TransacaoRequestDTO;
import com.acabou_o_mony.mony.dto.TransacaoResponseDTO;
import com.acabou_o_mony.mony.entity.*;
import com.acabou_o_mony.mony.enums.Status;
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
        transacao.setTipo_transacao(dto.getTipoTransacao());
        transacao.setValor(dto.getValor());
        transacao.setDthora(dto.getDthora());
        transacao.setDestinatario(dto.getDestinatario());
        transacao.setStatus(dto.getStatus());

        Optional<PessoaFisica> pessoaFisicaPorId = pessoaFisicaRepository.findById(dto.getRemetenteId());
        Optional<PessoaJuridica> pessoaJuridicaPorId = pessoaJuridicaRepository.findById(dto.getRemetenteId());

        if (pessoaFisicaPorId.isPresent()) {
            Cliente remetente = new PessoaFisica();
            remetente.setId(dto.getRemetenteId());
            transacao.setRemetente(remetente);
        }
        if (pessoaJuridicaPorId.isPresent()) {
            Cliente remetente = new PessoaJuridica();
            remetente.setId(dto.getRemetenteId());
            transacao.setRemetente(remetente);
        }

        Cartao cartao = new Cartao();
        cartao.setId(dto.getCartaoId());
        transacao.setCartao(cartao);

        Conta conta = new Conta();
        conta.setId(dto.getContaId());

        return transacao;
    }

    public TransacaoResponseDTO toResponse(Transacao transacao) {
        TransacaoResponseDTO dto = new TransacaoResponseDTO();
        dto.setId(transacao.getId());
        dto.setTipoTransacao(transacao.getTipo_transacao());
        dto.setValor(transacao.getValor());
        dto.setDthora(transacao.getDthora());
        dto.setDestinatario(transacao.getDestinatario());
        dto.setStatus((Status) transacao.getStatus());

        if (transacao.getRemetente() != null) {
            dto.setRemetenteId(transacao.getRemetente().getId());
        }

        if (transacao.getCartao() != null) {
            Cartao cartao = transacao.getCartao();
            CartaoResponseDTO cartaoDTO = new CartaoResponseDTO();
            cartaoDTO.setNome(cartao.getNome());
            cartaoDTO.setNumero(cartao.getNumero());
            cartaoDTO.setVencimento(cartao.getVencimento());
            cartaoDTO.setBandeira(cartao.getBandeira());
            cartaoDTO.setCredito(cartao.isCredito());
            cartaoDTO.setDebito(cartao.isDebito());
            cartaoDTO.setLimiteCredito(cartao.getLimiteCredito());
            if (cartao.getConta() != null) {
                cartaoDTO.setContaId(cartao.getConta().getId());
            }
            dto.setCartao(cartaoDTO);
        }

        return dto;
    }
}
