package com.acabou_o_mony.produto_service.enums;

public enum CategoriaProduto {
    ELETRONICOS("Eletrônicos"),
    ROUPAS_ACESSORIOS("Roupas e Acessórios"),
    ALIMENTOS_BEBIDAS("Alimentos e Bebidas"),
    MOVEIS_DECORACAO("Móveis e Decoração"),
    BRINQUEDOS_JOGOS("Brinquedos e Jogos"),
    COSMETICOS_HIGIENE("Cosméticos e Higiene"),
    LIVROS_PAPELARIA("Livros e Papelaria"),
    ESPORTES_LAZER("Esportes e Lazer"),
    AUTOMOTIVO("Automotivo"),
    FERRAMENTAS_CONSTRUAO("Ferramentas e Construção");

    private String descricao;

    CategoriaProduto(String descricao) {
        this.descricao = descricao;
    }

    public static CategoriaProduto fromString(String text) {
        for (CategoriaProduto categoria : CategoriaProduto.values()) {
            if (categoria.descricao.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma Categoria Encontrada");
    }
}
