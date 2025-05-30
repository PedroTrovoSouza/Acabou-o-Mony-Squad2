package com.acabou_o_mony.produto_service.mapper;


import com.acabou_o_mony.produto_service.dto.ListagemProdutoDTO;
import com.acabou_o_mony.produto_service.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperProduto {

//    @Mapping(target = "nome", source = "nome")
    Produto toEntity(ListagemProdutoDTO produtoDTO);

    //    @Mapping(target = "nome", source = "nome")
    ListagemProdutoDTO toListagemProdutoDTO(Produto entity);
}
