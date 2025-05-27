package contas.service.contas_service.mapper;


import contas.service.contas_service.dto.cliente.*;
import contas.service.contas_service.entity.PessoaFisica;
import contas.service.contas_service.entity.PessoaJuridica;

public class ClienteMapper {

    public static FisicaResponseDto toResponseDto(PessoaFisica pessoa){
        return new FisicaResponseDto(pessoa.getNome(), pessoa.getCpf(), pessoa.getEmail(), pessoa.getDataNascimento(), pessoa.getGenero());
    }

    public static JuridicaResponseDto toResponseDto(PessoaJuridica pessoa){
        return new JuridicaResponseDto(pessoa.getNome(), pessoa.getCnpj(), pessoa.getEmail(), pessoa.getDataFundacao());
    }

    public static PessoaFisica toEntity(FisicaRequestDto dtoCadastro) {
        return new PessoaFisica(dtoCadastro.nome(), dtoCadastro.cpf(), dtoCadastro.email(), dtoCadastro.dataNascimento(),
                dtoCadastro.genero());
    }

    public static PessoaJuridica toEntity(JuridicaRequestDto dtoCadastro) {
        return new PessoaJuridica(dtoCadastro.nome(), dtoCadastro.cnpj(), dtoCadastro.email(), dtoCadastro.dataFundacao());
    }

    public static PessoaFisica toEntity(FisicaAtualizacaoDto dto) {
        return new PessoaFisica(dto.nome(), dto.email(), dto.dataNascimento(), dto.genero());
    }
}
