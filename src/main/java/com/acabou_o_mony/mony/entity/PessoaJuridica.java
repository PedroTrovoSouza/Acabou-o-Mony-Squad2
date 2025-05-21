package com.acabou_o_mony.mony.entity;

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

    public PessoaJuridica(String nome, String cnpj, LocalDate dataFundacao) {
        super(nome);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
    }
}
