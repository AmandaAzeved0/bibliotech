package br.com.techlead.mapper.estoqueMapper;

import br.com.techlead.domain.EstoqueLivro;
import br.com.techlead.dto.response.EstoqueResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class EstoqueMapper {

    public static final EstoqueMapper INSTANCE = Mappers.getMapper(EstoqueMapper.class);

    public abstract EstoqueResponseDto toDto(EstoqueLivro entity);
}
