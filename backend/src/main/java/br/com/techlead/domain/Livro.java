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
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "disponivel", nullable = false)
    private Boolean disponivel = false;

    @Column(name = "estado", nullable = false, length = 10)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estoque_id", nullable = false)
    private EstoqueLivro estoque;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "criado_por")
    private Usuario criadoPor;

}