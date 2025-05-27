package com.acabou_o_mony.mony.mapper;


import com.acabou_o_mony.mony.dto.cartao.CartaoRequestDTO;
import com.acabou_o_mony.mony.dto.cartao.CartaoResponseDTO;
import com.acabou_o_mony.mony.entity.Cartao;
import org.springframework.stereotype.Component;

@Component
public class CartaoMapper {

    public Cartao toEntity(CartaoRequestDTO dto) {
        Cartao cartao = new Cartao();
        cartao.setNome(dto.getNome());
        cartao.setNumero(dto.getNumero());
        cartao.setVencimento(dto.getVencimento());
        cartao.setCvv(dto.getCvv());
        cartao.setBandeira(dto.getBandeira());
        cartao.setContaId(dto.getContaId());
        return cartao;
    }

    public CartaoResponseDTO toResponseDTO(Cartao entity) {
        CartaoResponseDTO dto = new CartaoResponseDTO();
        dto.setNome(entity.getNome());
        dto.setNumero(entity.getNumero());
        dto.setVencimento(entity.getVencimento());
        dto.setBandeira(entity.getBandeira());
        dto.setContaId(entity.getContaId());
        return dto;
    }
}
