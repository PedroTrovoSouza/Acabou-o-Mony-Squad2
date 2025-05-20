package com.acabou_o_mony.mony.repository;

import com.acabou_o_mony.mony.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpfOuCnpj(String cpfOuCnpj);
}
