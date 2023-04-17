package br.com.techlead.mapper.estoqueMapper;

import br.com.techlead.domain.EstoqueLivro;
import br.com.techlead.domain.Livro;
import br.com.techlead.dto.response.EstoqueResponseDto;

public class EstoqueMapperImpl extends EstoqueMapper {


    @Override
    public EstoqueResponseDto toDto(EstoqueLivro entity) {
        return EstoqueResponseDto.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .autor(entity.getAutor())
                .genero(entity.getGenero())
                .quantidadeTotal(entity.getQuantidade())
                .quantidadeDisponivel(entity.getLivros().stream().filter(Livro::getDisponivel).count())
                .build();
    }

}
