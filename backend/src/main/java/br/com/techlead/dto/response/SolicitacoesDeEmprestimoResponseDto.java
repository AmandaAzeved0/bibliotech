package br.com.techlead.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ApiModel("Dto para retornar solicitações de emprestimo de um livro")
public class SolicitacoesDeEmprestimoResponseDto {
            private Integer id;
            private LocalDate dataSoliciacao;
            private String status;
            private Integer usuarioId;
            private String nomeLivro;
            private Integer livroId;
            private Integer diasSolicitados;
}
