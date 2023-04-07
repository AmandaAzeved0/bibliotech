package br.com.techlead.dto.request;

import br.com.techlead.domain.Perfil;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel("DTO para cadastro de usuários")
public class UsuarioCadastroRequestDto {

    private String nome;
    @CPF
    private String cpf;
    @Email(message = "Email inválido")
    private String email;
    private String senha;
}
