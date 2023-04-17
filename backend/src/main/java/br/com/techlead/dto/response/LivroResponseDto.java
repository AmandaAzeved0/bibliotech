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
    private Integer id;
    private String titulo;
    private String autor;
    private String genero;
    private String estado;
    private Boolean disponivel;
    private Integer estoqueId;


}
