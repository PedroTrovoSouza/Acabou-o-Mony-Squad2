package contas.service.contas_service.entity;


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

    private PerfilEconomico perfilEconomico;

    private LocalDate dataNascimento;

    private Genero genero;

    public PessoaFisica(Long id, String nome, String cpf, PerfilEconomico perfilEconomico, LocalDate dataNascimento, Genero genero) {
        super(id, nome);
        this.cpf = cpf;
        this.perfilEconomico = perfilEconomico;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    public PessoaFisica(String nome, String cpf, LocalDate dataNascimento, Genero genero) {
        super(nome);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    public PessoaFisica(String nome, LocalDate dataNascimento, Genero genero) {
        super(nome);
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }
}
