package br.com.techlead.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ApiModel("DTO para login de usuários")
public class LoginDto {
    private String email;
    private String senha;

}
