package contas.service.contas_service.entity;

import contas.service.contas_service.enums.PerfilEconomico;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String login;

    private String password;

    private PerfilEconomico perfilEconomico;

    public Cliente(String nome, String email, String senha, PerfilEconomico perfilEconomico) {
        this.nome = nome;
        this.login = email;
        this.password = senha;
        this.perfilEconomico = perfilEconomico;
    }

    public Cliente(String nome, String email, String senha) {
        this.nome = nome;
        this.login = email;
        this.password = senha;
    }

    public void setUsername(String email) {
        this.login = email;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
