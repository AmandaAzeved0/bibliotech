package br.com.techlead.mapper.livroMapper;

import br.com.techlead.domain.EstoqueLivro;
import br.com.techlead.domain.Livro;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.request.LivroPostRequestDto;
import br.com.techlead.dto.response.LivroResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {
    public static final LivroMapper INSTANCE = Mappers.getMapper(LivroMapper.class);

   public abstract LivroResponseDto toDto(Livro entity);

    public abstract Livro toEntity(List<LivroPostRequestDto> dtos);

    public abstract Livro toEntity(LivroPostRequestDto dto, EstoqueLivro estoque, Usuario usuario);

}