package br.com.techlead.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "penalidade_usuario")
public class PenalidadeUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    @Column(name = "data_penalidade", nullable = false)
    private LocalDate dataPenalidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_de_penalidade_id", nullable = false)
    private TiposDePenalidade tipoDePenalidade;


    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status = false;


    @NotNull
    @Column(name = "data_penalidade_fim", nullable = false)
    private LocalDate dataPenalidadeFim;

    @Column(name = "data_bloqueio_fim")
    private LocalDate dataDeBloqueioFim;

}