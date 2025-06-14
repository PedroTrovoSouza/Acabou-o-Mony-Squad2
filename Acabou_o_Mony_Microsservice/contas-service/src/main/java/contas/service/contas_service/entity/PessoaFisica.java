package contas.service.contas_service.entity;


import contas.service.contas_service.dto.cliente.FisicaRequestDto;
import contas.service.contas_service.enums.Genero;
import contas.service.contas_service.enums.PerfilEconomico;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFisica extends Cliente{

    private String cpf;

    private LocalDate dataNascimento;

    private Genero genero;

    public PessoaFisica(FisicaRequestDto dto) {
        super(dto.nome(), dto.email(), dto.senha(), dto.perfilEconomico());
        this.cpf = dto.cpf();
        this.dataNascimento = dto.dataNascimento();
        this.genero = dto.genero();
    }

    public PessoaFisica(String nome, String email, String senha, PerfilEconomico perfilEconomico, String cpf, LocalDate dataNascimento, Genero genero) {
        super(nome, email, senha, perfilEconomico);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    public PessoaFisica(String nome, String login, String password, LocalDate dataNascimento, Genero genero) {
        super(nome, login, password);
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }


    public PessoaFisica(Long id, String nome, String login, String password, PerfilEconomico perfilEconomico, String cpf, LocalDate dataNascimento, Genero genero) {
        super(id, nome, login, password, perfilEconomico);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }
}
