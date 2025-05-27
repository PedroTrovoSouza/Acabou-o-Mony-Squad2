package com.acabou_o_mony.mony.mapper;

import com.acabou_o_mony.mony.dto.produto.CadastroProdutoDTO;
import com.acabou_o_mony.mony.dto.produto.ListagemProdutoDTO;
import com.acabou_o_mony.mony.entity.Produto;
import com.acabou_o_mony.mony.enums.CategoriaProduto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-27T09:41:28-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class MapperProdutoImpl implements MapperProduto {

    @Override
    public Produto toEntity(CadastroProdutoDTO produtoDTO) {
        if ( produtoDTO == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setNome( produtoDTO.nome() );
        produto.setPreco( produtoDTO.preco() );
        if ( produtoDTO.categoria() != null ) {
            produto.setCategoria( Enum.valueOf( CategoriaProduto.class, produtoDTO.categoria() ) );
        }
        produto.setQtd_estoque( produtoDTO.qtd_estoque() );

        return produto;
    }

    @Override
    public CadastroProdutoDTO toCadastroProdutoDTO(Produto entity) {
        if ( entity == null ) {
            return null;
        }

        String nome = null;
        String categoria = null;
        double preco = 0.0d;
        int qtd_estoque = 0;

        nome = entity.getNome();
        if ( entity.getCategoria() != null ) {
            categoria = entity.getCategoria().name();
        }
        preco = entity.getPreco();
        qtd_estoque = entity.getQtd_estoque();

        CadastroProdutoDTO cadastroProdutoDTO = new CadastroProdutoDTO( nome, categoria, preco, qtd_estoque );

        return cadastroProdutoDTO;
    }

    @Override
    public ListagemProdutoDTO toListagemProdutoDTO(Produto entity) {
        if ( entity == null ) {
            return null;
        }

        String nome = null;
        String categoria = null;
        double preco = 0.0d;
        int qtd_estoque = 0;

        nome = entity.getNome();
        if ( entity.getCategoria() != null ) {
            categoria = entity.getCategoria().name();
        }
        preco = entity.getPreco();
        qtd_estoque = entity.getQtd_estoque();

        ListagemProdutoDTO listagemProdutoDTO = new ListagemProdutoDTO( nome, categoria, preco, qtd_estoque );

        return listagemProdutoDTO;
    }
}
