package com.acabou_o_mony.produto_service.mapper;


import com.acabou_o_mony.produto_service.dto.ListagemProdutoDTO;
import com.acabou_o_mony.produto_service.entity.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperProduto {
    Produto toEntity(ListagemProdutoDTO produtoDTO);
    ListagemProdutoDTO toListagemProdutoDTO(Produto entity);
}
