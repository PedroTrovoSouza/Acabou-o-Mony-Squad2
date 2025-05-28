package contas.service.contas_service.repository;

import contas.service.contas_service.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByEmail(String email);

    Cliente getClienteByEmail(String email);
}
