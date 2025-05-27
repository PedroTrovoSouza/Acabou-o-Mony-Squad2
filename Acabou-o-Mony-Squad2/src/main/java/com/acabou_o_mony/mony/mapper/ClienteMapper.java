package com.acabou_o_mony.mony.mapper;

import com.acabou_o_mony.mony.dto.cliente.*;
import com.acabou_o_mony.mony.entity.Cliente;
import com.acabou_o_mony.mony.entity.PessoaFisica;
import com.acabou_o_mony.mony.entity.PessoaJuridica;

public class ClienteMapper {

    public static FisicaResponseDto toResponseDto(PessoaFisica pessoa){
        return new FisicaResponseDto(pessoa.getNome(), pessoa.getCpf(), pessoa.getDataNascimento(), pessoa.getGenero());
    }

    public static JuridicaResponseDto toResponseDto(PessoaJuridica pessoa){
        return new JuridicaResponseDto(pessoa.getNome(), pessoa.getCnpj(), pessoa.getDataFundacao());
    }

    public static PessoaFisica toEntity(FisicaRequestDto dtoCadastro) {
        return new PessoaFisica(dtoCadastro.nome(), dtoCadastro.cpf(), dtoCadastro.dataNascimento(),
                dtoCadastro.genero());
    }

    public static PessoaJuridica toEntity(JuridicaRequestDto dtoCadastro) {
        return new PessoaJuridica(dtoCadastro.nome(), dtoCadastro.cnpj(), dtoCadastro.dataFundacao());
    }

    public static PessoaFisica toEntity(FisicaAtualizacaoDto dto) {
        return new PessoaFisica(dto.nome(), dto.dataNascimento(), dto.genero());
    }
}
