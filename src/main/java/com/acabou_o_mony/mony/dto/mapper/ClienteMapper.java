package com.acabou_o_mony.mony.dto.mapper;

import com.acabou_o_mony.mony.dto.cliente.ClienteAtualizacaoDto;
import com.acabou_o_mony.mony.dto.cliente.ClienteRequestDto;
import com.acabou_o_mony.mony.dto.cliente.ClienteResponseDto;
import com.acabou_o_mony.mony.entity.Cliente;

public class ClienteMapper {

    public static ClienteResponseDto toResponseDto(Cliente cliente){
        return new ClienteResponseDto(cliente.getCpfOuCnpj(), cliente.getRazaoSocial(), cliente.getDataNascimento(),
                cliente.getGenero());
    }

    public static Cliente toEntity(ClienteRequestDto dtoCadastro) {
        return new Cliente(dtoCadastro.cpfOuCnpj(), dtoCadastro.razaoSocial(), dtoCadastro.dataNascimento(),
                dtoCadastro.genero());
    }

    public static Cliente toEntity(ClienteAtualizacaoDto dtoAtualizado) {
        return new Cliente(dtoAtualizado.dataNascimento(), dtoAtualizado.genero());
    }
}
