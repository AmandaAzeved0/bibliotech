package br.com.techlead.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DTO para retorno de livros")
public class LivroResponseDto {
    private Boolean disponivel;
    private String estado;
    private Integer estoqueId;
    private String titulo;
    private String autor;

}
