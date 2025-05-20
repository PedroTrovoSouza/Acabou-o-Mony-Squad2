package com.acabou_o_mony.mony.repository;

import com.acabou_o_mony.mony.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}
