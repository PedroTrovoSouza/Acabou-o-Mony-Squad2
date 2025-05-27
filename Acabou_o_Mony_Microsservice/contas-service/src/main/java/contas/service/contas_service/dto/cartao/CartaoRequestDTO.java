package contas.service.contas_service.dto.cartao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class CartaoRequestDTO {
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
