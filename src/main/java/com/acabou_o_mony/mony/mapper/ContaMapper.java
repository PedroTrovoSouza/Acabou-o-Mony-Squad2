package com.acabou_o_mony.mony.mapper;

import com.acabou_o_mony.mony.dto.conta.ContaAtualizacaoDto;
import com.acabou_o_mony.mony.dto.conta.ContaRequestDto;
import com.acabou_o_mony.mony.dto.conta.ContaResponseDto;
import com.acabou_o_mony.mony.entity.Cliente;
import com.acabou_o_mony.mony.entity.Conta;

public class ContaMapper {

    public static ContaResponseDto toResponse(Conta conta){
            return new ContaResponseDto(conta.getAgencia(), conta.getTipoConta(), conta.getSaldo(),
                    conta.getIsAtiva(), conta.getCliente().getId());
    }

    public static Conta toEntity(ContaRequestDto dto, Cliente cliente) {
        return new Conta(dto.agencia(), dto.tipoConta(),
                dto.isDebito(), dto.isCredito(), cliente);
    }

    public static Conta toEntity(ContaAtualizacaoDto dto) {
        return new Conta(dto.agencia(), dto.tipoConta());
    }
}
