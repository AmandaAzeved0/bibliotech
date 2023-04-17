package br.com.techlead.mapper.emprestimoMapper;

import br.com.techlead.domain.Emprestimo;
import br.com.techlead.domain.Livro;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.response.EmprestimoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public abstract class EmprestimoMapper {
    public static final EmprestimoMapper INSTANCE = Mappers.getMapper(EmprestimoMapper.class);

    public abstract Emprestimo toEntity(Usuario usuario, LocalDate dataInicio, LocalDate dataFim, Livro livro);

    public abstract EmprestimoResponseDto toDto(Emprestimo emprestimo);
}