package contas.service.contas_service.repository;

import contas.service.contas_service.entity.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    boolean existsByCpf(String cpf);

    PessoaFisica getPessoaFisicaByCpf(String cpf);

    boolean existsByLogin(String email);

    PessoaFisica getPessoaFisicaByLogin (String email);
}
