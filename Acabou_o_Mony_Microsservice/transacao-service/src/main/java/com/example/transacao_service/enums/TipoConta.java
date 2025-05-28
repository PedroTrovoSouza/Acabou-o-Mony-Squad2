package com.example.transacao_service.enums;

public enum TipoConta {
    CONTA_CORRENTE("Conta Corrente"),
    CONTA_POUPANCA("Conta poupança"),
    CONTA_SALARIO("Conta Salario");

    private String descricao;

    TipoConta(String descricao) {
        this.descricao = descricao;
    }

    public static TipoConta fromSring(String text){
        for (TipoConta conta : TipoConta.values()) {
            if (conta.descricao.equalsIgnoreCase(text)){
                return conta;
            }
        }
        throw new IllegalArgumentException("Tipo de razão social não é válida.");
    }
}
