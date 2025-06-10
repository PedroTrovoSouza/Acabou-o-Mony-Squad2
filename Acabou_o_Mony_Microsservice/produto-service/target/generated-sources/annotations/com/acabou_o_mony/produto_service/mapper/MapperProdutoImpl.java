package com.acabou_o_mony.produto_service.mapper;

import com.acabou_o_mony.produto_service.dto.CadastroProdutoDTO;
import com.acabou_o_mony.produto_service.dto.ListagemProdutoDTO;
import com.acabou_o_mony.produto_service.entity.Produto;
import com.acabou_o_mony.produto_service.enums.CategoriaProduto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-10T13:44:34-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class MapperProdutoImpl implements MapperProduto {

    @Override
    public Produto toEntity(ListagemProdutoDTO produtoDTO) {
        if ( produtoDTO == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setNome( produtoDTO.nome() );
        if ( produtoDTO.categoria() != null ) {
            produto.setCategoria( Enum.valueOf( CategoriaProduto.class, produtoDTO.categoria() ) );
        }
        produto.setPreco( produtoDTO.preco() );
        produto.setQtd_estoque( produtoDTO.qtd_estoque() );

        return produto;
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
        if ( entity.getPreco() != null ) {
            preco = entity.getPreco();
        }
        qtd_estoque = entity.getQtd_estoque();

        ListagemProdutoDTO listagemProdutoDTO = new ListagemProdutoDTO( nome, categoria, preco, qtd_estoque );

        return listagemProdutoDTO;
    }

    @Override
    public CadastroProdutoDTO toCadastroDto(CadastroProdutoDTO produtoDTO) {
        if ( produtoDTO == null ) {
            return null;
        }

        String nome = null;
        String categoria = null;
        double preco = 0.0d;
        int qtd_estoque = 0;

        nome = produtoDTO.nome();
        categoria = produtoDTO.categoria();
        preco = produtoDTO.preco();
        qtd_estoque = produtoDTO.qtd_estoque();

        CadastroProdutoDTO cadastroProdutoDTO = new CadastroProdutoDTO( nome, categoria, preco, qtd_estoque );

        return cadastroProdutoDTO;
    }
}
