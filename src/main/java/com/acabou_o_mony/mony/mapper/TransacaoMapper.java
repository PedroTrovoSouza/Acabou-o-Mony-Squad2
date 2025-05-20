package com.acabou_o_mony.mony.mapper;

import com.acabou_o_mony.mony.dto.CartaoResponseDTO;
import com.acabou_o_mony.mony.dto.TransacaoRequestDTO;
import com.acabou_o_mony.mony.dto.TransacaoResponseDTO;
import com.acabou_o_mony.mony.entity.Cartao;
import com.acabou_o_mony.mony.entity.Cliente;
import com.acabou_o_mony.mony.entity.Conta;
import com.acabou_o_mony.mony.entity.Transacao;
import com.acabou_o_mony.mony.enums.Status;

public class TransacaoMapper {

    public static Transacao toEntity(TransacaoRequestDTO dto) {
        Transacao transacao = new Transacao();
        transacao.setTipo_transacao(dto.getTipoTransacao());
        transacao.setValor(dto.getValor());
        transacao.setDthora(dto.getDthora());
        transacao.setDestinatario(dto.getDestinatario());
        transacao.setStatus(dto.getStatus());

        Cliente remetente = new Cliente();
        remetente.setId(dto.getRemetenteId());
        transacao.setRemetente(remetente);

        Cartao cartao = new Cartao();
        cartao.setId(dto.getCartaoId());
        transacao.setCartao(cartao);

        Conta conta = new Conta();
        conta.setId(dto.getContaId());
        transacao.setConta(conta);

        return transacao;
    }

    public static TransacaoResponseDTO toResponse(Transacao transacao) {
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

        if (transacao.getConta() != null) {
            dto.setContaId(transacao.getConta().getId());
        }

        return dto;
    }
}
