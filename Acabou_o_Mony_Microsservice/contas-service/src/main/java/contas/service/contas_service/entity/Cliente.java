package contas.service.contas_service.entity;

import contas.service.contas_service.enums.PerfilEconomico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    private PerfilEconomico perfilEconomico;

    public Cliente(String nome, String email, PerfilEconomico perfilEconomico) {
        this.nome = nome;
        this.email = email;
        this.perfilEconomico = perfilEconomico;
    }

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
}
