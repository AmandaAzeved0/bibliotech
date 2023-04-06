package br.com.techlead.mapper.livroMapper;

import br.com.techlead.domain.EstoqueLivro;
import br.com.techlead.domain.Livro;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.request.LivroPostRequestDto;
import br.com.techlead.dto.response.LivroResponseDto;

import java.util.List;

public class LivroMapperImpl extends LivroMapper {


    @Override
    public LivroResponseDto toDto(Livro entity) {
        LivroResponseDto dto  = LivroResponseDto.builder()
                .estoqueId(entity.getEstoque().getId())
                .autor(entity.getEstoque().getAutor())
                .titulo(entity.getEstoque().getTitulo())
                .disponivel(entity.getDisponivel())
                .estado(entity.getEstado())
                .build();
        return dto;
    }

    @Override
    public Livro toEntity(List<LivroPostRequestDto> dtos) {
        return null;
    }


    @Override
    public Livro toEntity(LivroPostRequestDto dto, EstoqueLivro estoque, Usuario usuario) {
        Livro entity = Livro.builder()
                .disponivel(true)
                .estado(dto.getEstado())
                .estoque(estoque)
                .criadoPor(usuario)
                .build();
        return entity;
    }




}
