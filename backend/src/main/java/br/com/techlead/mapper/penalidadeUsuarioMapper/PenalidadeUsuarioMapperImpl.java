package br.com.techlead.mapper.penalidadeUsuarioMapper;

import br.com.techlead.domain.PenalidadeUsuario;
import br.com.techlead.dto.PenalidadeUsuarioDto;

public class PenalidadeUsuarioMapperImpl extends PenalidadeUsuarioMapper{
    @Override
    public PenalidadeUsuario toEntity(PenalidadeUsuarioDto dto) {
        PenalidadeUsuario penalidadeUsuario = PenalidadeUsuario.builder()
                .dataPenalidade(dto.getDataPenalidade())
                .usuario(dto.getUsuario())
                .tipoDePenalidade(dto.getTipoDePenalidade())
                .dataPenalidadeFim(dto.getDataPenalidadeFim())
                .dataDeBloqueioFim(dto.getDataDeBloqueioFim())
                .status(dto.getStatus())
                .build();
        return penalidadeUsuario;
    }
}
