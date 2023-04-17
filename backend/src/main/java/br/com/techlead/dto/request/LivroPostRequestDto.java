package br.com.techlead.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel("Representa um livro para cadastro individual")
public class LivroPostRequestDto {

    private String titulo;
    private String genero;
    private String autor;
    private String estado;
    private Integer quantidade;



}
