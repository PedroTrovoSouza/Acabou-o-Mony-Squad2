package com.acabou_o_mony.mony.repository;

import com.acabou_o_mony.mony.entity.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    boolean existsByCnpj(String cnpj);

    PessoaJuridica getPessoaJuridicaByCnpj(String cnpj);
}
