package br.com.techlead.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Dto para atualizar o estado de um livro")
public class LivroPutRequestDto {
    private String estado;
}
