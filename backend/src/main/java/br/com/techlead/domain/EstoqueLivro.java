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
@Table(name = "estoque_livros")
public class EstoqueLivro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "genero", nullable = false, length = 100)
    private String genero;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "autor", nullable = false, length = 100)
    private String autor;


}