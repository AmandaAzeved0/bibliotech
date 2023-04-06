package br.com.techlead.domain;

import br.com.techlead.enums.StatusSolicitacaoEmprestimoEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "solicitacoes_de_emprestimo")
public class SolicitacaoDeEmprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id")
    private Livro livro;


    @Column(name = "data_reserva", nullable = false)
    private LocalDate dataSoliciacao;


    @Column(name = "status", nullable = false, length = 20)
    private String status;


    @NotNull
    @Column(name = "quantidade_dias", nullable = false)
    private Integer quantidadeDias;

}