package com.acabou_o_mony.pedido.repository;

import com.acabou_o_mony.pedido.dto.PedidoResponseDTO;
import com.acabou_o_mony.pedido.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(value = """
    SELECT 
        p.valor_total AS valorTotal,
        p.data_pedido AS dataPedido,
        pr.nome AS nomeProduto,
        p.status AS statusPedido
    FROM pedido p
    JOIN produto pr ON p.produto_id = pr.id
    WHERE p.id_pedido = :id
    """, nativeQuery = true)
    PedidoResponseDTO buscarPedidoComNomeDoProduto(@Param("id") Long id);

}