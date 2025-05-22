package com.acabou_o_mony.mony.repository;

import com.acabou_o_mony.mony.dto.PedidoResponseDTO;
import com.acabou_o_mony.mony.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT new com.acabou_o_mony.mony.dto.PedidoResponseDTO(p.valorTotal, p.dataPedido, pr.nome, p.status) " +
            "FROM Pedido p JOIN p.produto pr " +
            "WHERE p.id = :id")
    PedidoResponseDTO buscarPedidoComNomeDoProduto(@Param("id") Long id);

    Pedido findByNumero(long numero);
}
