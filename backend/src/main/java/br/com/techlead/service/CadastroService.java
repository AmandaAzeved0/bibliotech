package br.com.techlead.service;

import br.com.techlead.domain.Perfil;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.request.UsuarioCadastroRequestDto;
import br.com.techlead.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastroService {
    private final UsuarioService usuarioService;
    private final PerfilRepository perfilRepository;
    public Usuario save(UsuarioCadastroRequestDto usuarioDto) {

        //TODO VERIFICAR MELHOR IMPLEMENTACAO DE PERFIL E CADASTRAR SOMENTE CLIENTES
        Perfil perfil = perfilRepository.findById(usuarioDto.getPerfilId()).orElseThrow(() -> new RuntimeException("Perfil não encontrado！"));
        Usuario entity = Usuario.builder()
                .nome(usuarioDto.getNome())
                .cpf(usuarioDto.getCpf())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .perfil(perfil)
                .build();
        return usuarioService.save(entity);
    }
}
