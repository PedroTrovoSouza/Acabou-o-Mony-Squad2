package com.acabou_o_mony.mony.mapper;


import com.acabou_o_mony.mony.dto.produto.CadastroProdutoDTO;
import com.acabou_o_mony.mony.dto.produto.ListagemProdutoDTO;
import com.acabou_o_mony.mony.entity.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperProduto {
    Produto toEntity(ListagemProdutoDTO produtoDTO);
    CadastroProdutoDTO toCadastroProdutoDTO(Produto entity);
    ListagemProdutoDTO toListagemProdutoDTO(Produto entity);
}
