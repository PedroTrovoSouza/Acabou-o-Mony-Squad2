package com.acabou_o_mony.mony.repository;

import com.acabou_o_mony.mony.entity.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    boolean existsByCpf(String cpf);

    PessoaFisica getPessoaFisicaByCpf(String cpf);
}
