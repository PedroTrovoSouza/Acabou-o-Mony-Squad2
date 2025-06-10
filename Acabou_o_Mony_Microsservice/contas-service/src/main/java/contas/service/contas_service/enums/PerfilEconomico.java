package contas.service.contas_service.enums;

import java.math.BigDecimal;

public enum PerfilEconomico {
    BAIXO (BigDecimal.valueOf(1500.0)),
    MEDIO (BigDecimal.valueOf(3000.0)),
    ALTO (BigDecimal.valueOf(6000.0)),
    EMPRESA (BigDecimal.valueOf(10000.0));
    public BigDecimal limite;

    PerfilEconomico(BigDecimal limite) {
        this.limite = limite;
    }

}
