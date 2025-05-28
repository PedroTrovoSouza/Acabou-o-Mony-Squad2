package contas.service.contas_service.entity;

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

    public PessoaJuridica(Long id, String nome, String email, String cnpj, LocalDate dataFundacao) {
        super(id, nome, email);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
    }

    public PessoaJuridica(String nome, String email, String cnpj, LocalDate dataFundacao) {
        super(nome, email);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
    }


}
