package com.acabou_o_mony.mony.enums;

public enum TipoRazaoSocial {
    PESSOA_FISICA("Pessoa Física"),
    PESSOA_JURIDICA("Pessoa Jurídica");

    private String descricao;

    TipoRazaoSocial(String descricao) {
        this.descricao = descricao;
    }

    public static TipoRazaoSocial fromSring(String text){
        for (TipoRazaoSocial razao : TipoRazaoSocial.values()) {
            if (razao.descricao.equalsIgnoreCase(text)){
                return razao;
            }
        }
        throw new IllegalArgumentException("Tipo de razão social não é válida.");
    }
}
