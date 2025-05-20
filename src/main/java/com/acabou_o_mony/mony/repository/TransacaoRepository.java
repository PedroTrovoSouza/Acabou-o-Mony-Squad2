package com.acabou_o_mony.mony.repository;

import com.acabou_o_mony.mony.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
