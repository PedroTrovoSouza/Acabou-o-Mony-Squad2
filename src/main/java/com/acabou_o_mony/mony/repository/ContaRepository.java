package com.acabou_o_mony.mony.repository;

import com.acabou_o_mony.mony.entity.Cliente;
import com.acabou_o_mony.mony.entity.Conta;
import com.acabou_o_mony.mony.enums.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    boolean existsByTipoContaAndCliente(TipoConta tipoConta, Cliente cliente);

    boolean existsByTipoContaAndClienteAndIdNot(TipoConta tipoConta, Cliente cliente, Long id);

}
