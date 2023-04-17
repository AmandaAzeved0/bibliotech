package br.com.techlead.mapper.usuarioMapper;

import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.response.EmprestimoResponseDto;
import br.com.techlead.dto.response.LivroResponseDto;
import br.com.techlead.dto.response.SolicitacoesDeEmprestimoResponseDto;
import br.com.techlead.dto.response.UsuarioResponseDto;

import java.util.List;

public class UsuarioMapperImpl extends UsuarioMapper {


    @Override
    public UsuarioResponseDto toDto(
            Usuario usuario,
            List<EmprestimoResponseDto> emprestimos,
            List<LivroResponseDto> livrosCadasrtados,
            List<SolicitacoesDeEmprestimoResponseDto> solicitacoesDeEmprestimo) {
        return UsuarioResponseDto.builder()
                .nome(usuario.getNome())
                .cpf(usuario.getCpf())
                .email(usuario.getEmail())
                .perfil(usuario.getPerfil().getNome())
                .statusDeBloqueio(usuario.getStatusDeBloqueio())
                .emprestimos(emprestimos)
                .livrosCadasrtados(livrosCadasrtados)
                .solicitacoesDeEmprestimo(solicitacoesDeEmprestimo)
                .build();
    }
}
