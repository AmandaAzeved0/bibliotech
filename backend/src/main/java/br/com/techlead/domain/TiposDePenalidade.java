package br.com.techlead.domain;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tipos_de_penalidade")
public class TiposDePenalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

    @Column(name = "dias_de_penalidade", nullable = false)
    private Short diasDePenalidade;

    @Column(name = "com_bloqueio", nullable = false)
    private Boolean comBloqueio = false;

    @OneToMany(mappedBy = "tipoDePenalidade")
    private Set<PenalidadeUsuario> penalidadeUsuarios = new LinkedHashSet<>();

}