package br.com.techlead.dto;

import br.com.techlead.domain.Emprestimo;
import br.com.techlead.domain.PenalidadeUsuario;
import br.com.techlead.domain.Perfil;
import br.com.techlead.domain.SolicitacaoDeEmprestimo;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("")
public class UsuarioDto extends AbstractDto<Integer> {


    private String nome;

    private String cpf;

    private String email;

    private String senha;
    @NotNull
    private Boolean statusDeBloqueio;
    private Perfil perfil;
    private Set<PenalidadeUsuario> penalidadeUsuarios;
    private Set<Emprestimo> emprestimos;
    private Set<SolicitacaoDeEmprestimo> reservas;


}