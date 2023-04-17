package br.com.techlead.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ApiModel("DTO para mudar senha de usuários")
public class MudarSenhaRequestDto {
    private String senhaNova;
    private String senhaAtual;

    private String senhaNovaConfirmacao;
}
