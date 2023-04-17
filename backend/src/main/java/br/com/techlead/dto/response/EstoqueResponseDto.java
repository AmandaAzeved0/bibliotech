package br.com.techlead.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DTO para retorno de dados do estoque")
public class EstoqueResponseDto {
    private Integer id;
    private String titulo;
    private String autor;
    private String genero;
    private Integer quantidadeTotal;

    private Long quantidadeDisponivel;

}
