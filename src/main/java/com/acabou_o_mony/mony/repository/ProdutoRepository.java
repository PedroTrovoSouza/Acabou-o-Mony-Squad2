package com.acabou_o_mony.mony.repository;

import com.acabou_o_mony.mony.entity.Produto;
import com.acabou_o_mony.mony.enums.CategoriaProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Boolean existsByNome(String nome);

    @Modifying
    @Transactional
    @Query("UPDATE Produto u set u.nome = :nome WHERE u.id = :id")
    int atualizarNome(Long id , String nome);

    @Modifying
    @Transactional
    @Query("UPDATE Produto u set u.categoria = :categoria WHERE u.id = :id")
    int atualizarCategoria(Long id , CategoriaProduto categoria);

    @Modifying
    @Transactional
    @Query("UPDATE Produto u set u.preco = :preco WHERE u.id = :id")
    int atualizarPreco(Long id , Double preco);

    @Modifying
    @Transactional
    @Query("UPDATE Produto u set u.qtd_estoque = :qtd_estoque WHERE u.id = :id")
    int atualizarQtdEstoque(Long id , int qtd_estoque);

    Optional<Produto> findByNome(String nome);
}
