package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.dto.cartao.CartaoRequestDTO;
import com.acabou_o_mony.mony.dto.cartao.CartaoResponseDTO;
import com.acabou_o_mony.mony.entity.Cartao;
import com.acabou_o_mony.mony.entity.Pedido;
import com.acabou_o_mony.mony.mapper.CartaoMapper;
import com.acabou_o_mony.mony.repository.CartaoRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoMapper mapper;

    @Autowired
    private CartaoRepository repository;

    public CartaoResponseDTO salvarCartao(CartaoRequestDTO dto) {
        Cartao cartaoEntidade = mapper.toEntity(dto);
        Optional<Cartao> cartaoExistente = repository.findById(cartaoEntidade.getId());

        if (cartaoExistente.isEmpty()) {
            return null;
        }

        Cartao cartaoSalvar = repository.save(cartaoEntidade);
        return mapper.toResponseDTO(cartaoSalvar);
    }

    public CartaoResponseDTO listarCartaoPorId(Long id) {
        Optional<Cartao> cartaoPorId = repository.findById(id);
        if(cartaoPorId.isEmpty()) {
            return null;
        }

        Cartao cartao = cartaoPorId.get();
        return mapper.toResponseDTO(cartao);
    }

    public CartaoResponseDTO atualizarCartao(Long id, CartaoRequestDTO dto) {
        Cartao existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado com ID: " + id));

        existente.setNome(dto.getNome());
        existente.setNumero(dto.getNumero());
        existente.setVencimento(dto.getVencimento());
        existente.setCvv(dto.getCvv());
        existente.setBandeira(dto.getBandeira());
        existente.setContaId(dto.getContaId());

        Cartao atualizado = repository.save(existente);
        return mapper.toResponseDTO(atualizado);
    }


    public boolean deletarPorId (Long id) {
        Optional<Cartao> cartaoDeletado = repository.findById(id);

        if (cartaoDeletado.isEmpty()){
            return false;
        }

        repository.delete(cartaoDeletado.get());
        return true;
    }
}
