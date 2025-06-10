package contas.service.contas_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String numero;
    private LocalDate vencimento;
    private String cvv;
    private String bandeira;
    private boolean isCredito;
    private boolean isDebito;
    private BigDecimal limiteCredito;
    private Long contaId;
}
