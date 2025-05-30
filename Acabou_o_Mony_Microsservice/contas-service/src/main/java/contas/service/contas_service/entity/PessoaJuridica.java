package contas.service.contas_service.entity;

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
public class PessoaJuridica extends Cliente{

    private String cnpj;

    private LocalDate dataFundacao;

    public PessoaJuridica(Long id, String nome, String email, PerfilEconomico perfilEconomico, String cnpj, LocalDate dataFundacao) {
        super(id, nome, email, perfilEconomico);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
    }

    public PessoaJuridica(String nome, String email, String cnpj, LocalDate dataFundacao) {
        super(nome, email);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
    }

    public PessoaJuridica(String nome, String email, PerfilEconomico perfilEconomico, String cnpj, LocalDate dataFundacao) {
        super(nome, email, perfilEconomico);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
    }
}
