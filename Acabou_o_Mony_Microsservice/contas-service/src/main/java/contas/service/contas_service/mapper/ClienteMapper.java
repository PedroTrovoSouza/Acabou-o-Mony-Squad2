package contas.service.contas_service.mapper;


import contas.service.contas_service.dto.cliente.*;
import contas.service.contas_service.entity.Cliente;
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
        return new PessoaFisica(dtoCadastro.nome(), dtoCadastro.email(), dtoCadastro.senha(), dtoCadastro.perfilEconomico(), dtoCadastro.cpf(),  dtoCadastro.dataNascimento(),
                dtoCadastro.genero());
    }

    public static PessoaJuridica toEntity(JuridicaRequestDto dtoCadastro) {
        return new PessoaJuridica(dtoCadastro.nome(), dtoCadastro.email(), "senha", dtoCadastro.perfilEconomico(), dtoCadastro.cnpj(),  dtoCadastro.dataFundacao());
    }

    public static PessoaFisica toEntity(FisicaAtualizacaoDto dto) {
        return new PessoaFisica(dto.nome(), dto.email(), dto.senha(), dto.dataNascimento(), dto.genero());
    }

    public Cliente of(ClienteDto clienteCraicao) {
        Cliente cliente = new Cliente();

        cliente.setEmail(clienteCraicao.getEmail());
        cliente.setNome(clienteCraicao.getNome());
        cliente.setSenha(clienteCraicao.getSenha());

        return cliente;
    }

    public Cliente of(ClienteLoginDto clienteLoginDto) {
        Cliente cliente = new Cliente();

        cliente.setEmail(clienteLoginDto.getEmail());
        cliente.setSenha(cliente.getSenha());

        return cliente;
    }

    public static ClienteTokenDto of(Cliente cliente, String token) {
        ClienteTokenDto clienteTokenDto = new ClienteTokenDto();

        clienteTokenDto.setId(cliente.getId());
        clienteTokenDto.setNome(cliente.getNome());
        clienteTokenDto.setEmail(cliente.getEmail());
        clienteTokenDto.setToken(token);

        return clienteTokenDto;
    }

    public static ClienteListarDto of(Cliente cliente) {
        ClienteListarDto clienteListarDto = new ClienteListarDto();

        clienteListarDto.setId(cliente.getId());
        clienteListarDto.setEmail(cliente.getEmail());
        clienteListarDto.setNome(cliente.getNome());

        return clienteListarDto;
    }
}
