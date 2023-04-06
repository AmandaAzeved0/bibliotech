package br.com.techlead.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ApiModel("Dto para gerar solicitação de emprestimo de um livro")
public class SolicitacaoDeEmprestimoPostRequestDto {
    private Integer livroId;
    private Integer quantidadeDias;
}
