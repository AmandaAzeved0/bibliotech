package br.com.techlead.dto.request;

import br.com.techlead.enums.StatusSolicitacaoEmprestimoEnum;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DTO atualização se solicitação de emprestimo")
public class SolicitacaoDeemprestimoPutResquestDto {
    private StatusSolicitacaoEmprestimoEnum status;
    private Integer solicitacaoEmprestimoId;
}
