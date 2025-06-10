package contas.service.contas_service.repository;


import contas.service.contas_service.entity.Cliente;
import contas.service.contas_service.entity.Conta;
import contas.service.contas_service.enums.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    boolean existsByTipoContaAndCliente(TipoConta tipoConta, Cliente cliente);

    boolean existsByTipoContaAndClienteAndIdNot(TipoConta tipoConta, Cliente cliente, Long id);
}
