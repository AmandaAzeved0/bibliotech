package br.com.techlead.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DTO para retorno de dados de uma usuário")
public class UsuarioResponseDto {

    private String nome;
    private String cpf;
    private String email;
    private String perfil;
    private Boolean statusDeBloqueio;
    private List<LivroResponseDto> livrosCadasrtados;
    private List<EmprestimoResponseDto> emprestimos;

    private List<SolicitacoesDeEmprestimoResponseDto> solicitacoesDeEmprestimo;


}
