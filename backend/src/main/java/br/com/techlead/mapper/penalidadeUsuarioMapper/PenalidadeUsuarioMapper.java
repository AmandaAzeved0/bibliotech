package br.com.techlead.mapper.penalidadeUsuarioMapper;

import br.com.techlead.domain.PenalidadeUsuario;
import br.com.techlead.dto.PenalidadeUsuarioDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PenalidadeUsuarioMapper {
    public static final PenalidadeUsuarioMapper INSTANCE = Mappers.getMapper(PenalidadeUsuarioMapper.class);

    public abstract PenalidadeUsuario toEntity(PenalidadeUsuarioDto dto);
}