package contas.service.contas_service.repository;

import contas.service.contas_service.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByLogin(String email);

    Cliente getClienteByLogin(String email);

    UserDetails findByLogin(String login);
}
