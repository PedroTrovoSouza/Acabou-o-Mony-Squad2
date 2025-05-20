package com.acabou_o_mony.mony.entity;

import com.acabou_o_mony.mony.enums.Genero;
import com.acabou_o_mony.mony.enums.PerfilEconomico;
import com.acabou_o_mony.mony.enums.TipoRazaoSocial;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpfOuCnpj;

    private TipoRazaoSocial razaoSocial;

    private PerfilEconomico perfilEconomico;

    private LocalDate dataNascimento;

    private Genero genero;

    public Cliente(String cpfOuCnpj, TipoRazaoSocial razaoSocial, LocalDate dataNascimento, Genero genero) {
        this.cpfOuCnpj = cpfOuCnpj;
        this.razaoSocial = razaoSocial;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    public Cliente(LocalDate dataNascimento, Genero genero) {
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

}
