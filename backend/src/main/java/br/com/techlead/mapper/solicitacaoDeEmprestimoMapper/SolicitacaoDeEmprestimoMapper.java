package br.com.techlead.mapper.solicitacaoDeEmprestimoMapper;


import br.com.techlead.domain.Livro;
import br.com.techlead.domain.SolicitacaoDeEmprestimo;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.response.SolicitacoesDeEmprestimoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public abstract class SolicitacaoDeEmprestimoMapper {
    public static final SolicitacaoDeEmprestimoMapper INSTANCE = Mappers.getMapper(SolicitacaoDeEmprestimoMapper.class);


    public abstract SolicitacaoDeEmprestimo toEntity(
            Usuario usuario,
            LocalDate dataSolicitacao,
            String status,
            Livro livro, Integer quantidadeDias);

    public abstract SolicitacoesDeEmprestimoResponseDto toDto(SolicitacaoDeEmprestimo solicitacao);
}