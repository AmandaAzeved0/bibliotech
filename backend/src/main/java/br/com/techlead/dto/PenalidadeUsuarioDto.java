package br.com.techlead.dto;

import br.com.techlead.domain.TiposDePenalidade;
import br.com.techlead.domain.Usuario;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ApiModel("Dto para penalidade de usuário")
public class PenalidadeUsuarioDto extends AbstractDto<Integer> {
    private Usuario usuario;
    private LocalDate dataPenalidade;
    private TiposDePenalidade tipoDePenalidade;

    private Boolean status;
    private LocalDate dataPenalidadeFim;

    private LocalDate dataDeBloqueioFim;

}