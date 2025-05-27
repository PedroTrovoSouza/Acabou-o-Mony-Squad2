package contas.service.contas_service.repository;

import contas.service.contas_service.entity.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    boolean existsByCnpj(String cnpj);

    PessoaJuridica getPessoaJuridicaByCnpj(String cnpj);
}
