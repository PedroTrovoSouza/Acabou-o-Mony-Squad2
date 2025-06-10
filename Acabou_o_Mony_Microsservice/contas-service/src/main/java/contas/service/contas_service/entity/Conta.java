package contas.service.contas_service.entity;


import contas.service.contas_service.enums.TipoConta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String agencia;

    private TipoConta tipoConta;

    private BigDecimal saldo;

    private Boolean isDebito;

    private Boolean isCredito;

    private BigDecimal limiteCredito;

    private Boolean isAtiva;

    @ManyToOne
    private Cliente cliente;

    public Conta(String agencia, TipoConta tipoConta, Boolean isDebito, Boolean isCredito, Cliente cliente) {
        this.agencia = agencia;
        this.tipoConta = tipoConta;
        this.isDebito = isDebito;
        this.isCredito = isCredito;
        this.cliente = cliente;
    }

    public Conta(String agencia, TipoConta tipoConta) {
        this.agencia = agencia;
        this.tipoConta = tipoConta;
    }

    public Conta(String agencia, TipoConta tipoConta, BigDecimal saldo, Boolean isDebito, Boolean isCredito, BigDecimal limiteCredito, Boolean isAtiva, Cliente cliente) {
        this.agencia = agencia;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        this.isDebito = isDebito;
        this.isCredito = isCredito;
        this.limiteCredito = limiteCredito;
        this.isAtiva = isAtiva;
        this.cliente = cliente;
    }

    public BigDecimal atualizarSaldo(BigDecimal valor){
        this.saldo = saldo.add(valor);
        return saldo;
    }

}
