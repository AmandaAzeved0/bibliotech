package br.com.techlead.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "usuarios")
public class Usuario implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "senha", nullable = false, length = 100)
    private String senha;

    @Column(name = "status_de_bloqueio", nullable = false)
    private Boolean statusDeBloqueio = false;

    @NotNull
    @Column(name = "senha_provisoria_ativa", nullable = false)
    private Boolean senhaProvisoriaAtiva = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @OneToMany(mappedBy = "usuario")
    private Set<PenalidadeUsuario> penalidadeUsuarios = new LinkedHashSet<>();

    @OneToMany(mappedBy = "usuario")
    private Set<Emprestimo> emprestimos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "usuario")
    private Set<SolicitacaoDeEmprestimo> reservas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "criadoPor")
    private Set<Livro> livros = new LinkedHashSet<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(perfil.getSigla());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {

        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !statusDeBloqueio;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !statusDeBloqueio;
    }
}
