package com.acabou_o_mony.mony.enums;

import java.math.BigDecimal;

public enum PerfilEconomico {
    BAIXO (BigDecimal.valueOf(1500.0)),
    MEDIO (BigDecimal.valueOf(3000.0)),
    ALTO (BigDecimal.valueOf(6000.0));
    private BigDecimal limite;

    PerfilEconomico(BigDecimal limite) {
        this.limite = limite;
    }

}
