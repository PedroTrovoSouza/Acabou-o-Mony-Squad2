package com.acabou_o_mony.produto_service.repository;

import com.acabou_o_mony.produto_service.entity.Produto;
import com.acabou_o_mony.produto_service.enums.CategoriaProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Boolean existsByNome(String nome);

    @Modifying
    @Transactional
    @Query("UPDATE Produto u set u.nome = :nome WHERE u.id = :id")
    int atualizarNome(@Param("id") Long id, @Param("nome") String nome);

    @Modifying
    @Transactional
    @Query("UPDATE Produto u set u.categoria = :categoria WHERE u.id = :id")
    int atualizarCategoria(@Param("id") Long id, @Param("categoria") CategoriaProduto categoria);

    @Modifying
    @Transactional
    @Query("UPDATE Produto u set u.preco = :preco WHERE u.id = :id")
    int atualizarPreco(@Param("id") Long id, @Param("preco") Double preco);

    @Modifying
    @Transactional
    @Query("UPDATE Produto u set u.qtd_estoque = :qtd_estoque WHERE u.id = :id")
    int atualizarQtdEstoque(@Param("id") Long id, @Param("qtd_estoque") int qtd_estoque);

    Optional<Produto> findByNome(String nome);

    @Query(value = "SELECT * FROM produto WHERE LOWER(nome) LIKE LOWER(CONCAT('%', :nome, '%'))", nativeQuery = true)
    List<Produto> buscarPorNome(@Param("nome") String nome);

}
