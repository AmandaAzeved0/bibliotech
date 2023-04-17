package br.com.techlead.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DTO para retorno de informações de um empréstimo")
public class EmprestimoResponseDto {
    private Integer idDoLivro;
    private String tituloDoLivro;
    private LocalDate dataDeEmprestimoInicio;
    private LocalDate dataDeEmprestimoFim;
    private LocalDate dataDeDevolucao;
}
