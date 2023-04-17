package br.com.techlead.mapper.usuarioMapper;

import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.response.EmprestimoResponseDto;
import br.com.techlead.dto.response.LivroResponseDto;
import br.com.techlead.dto.response.SolicitacoesDeEmprestimoResponseDto;
import br.com.techlead.dto.response.UsuarioResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {

    public static final UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    public abstract UsuarioResponseDto toDto(Usuario usuario, List<EmprestimoResponseDto> emprestimos, List<LivroResponseDto> livrosCadasrtados, List<SolicitacoesDeEmprestimoResponseDto> solicitacoesDeEmprestimo);
}
