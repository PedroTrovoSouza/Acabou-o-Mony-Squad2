package com.acabou_o_mony.mony.enums;

public enum TipoCliente {
    PESSOA_FISICA("Pessoa Física"),
    PESSOA_JURIDICA("Pessoa Jurídica");

    private String descricao;

    TipoCliente(String descricao) {
        this.descricao = descricao;
    }

    public static TipoCliente fromSring(String text){
        for (TipoCliente razao : TipoCliente.values()) {
            if (razao.descricao.equalsIgnoreCase(text)){
                return razao;
            }
        }
        throw new IllegalArgumentException("Tipo de razão social não é válida.");
    }
}
