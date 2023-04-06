package br.com.techlead.service;

import br.com.techlead.domain.Usuario;
import br.com.techlead.exception.BadRequestException;
import br.com.techlead.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String USUARIO_NAO_ENCONTRADO_MSG = "Usuário com email %s não encontrado！";


    private Usuario findEntityById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado！"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USUARIO_NAO_ENCONTRADO_MSG, email)));
    }


    public Usuario save(Usuario entity) {
        repository.findByEmail(entity.getEmail()).ifPresent(usuario -> {
            throw new BadRequestException("Email já cadastrado");
        });
        String senhaEncript = bCryptPasswordEncoder.encode(entity.getSenha());
        entity.setSenha(senhaEncript);
        entity.setStatusDeBloqueio(false);
        return repository.save(entity);


    }

    //TODO VERIFICAR A TROCA DOS CHAMADOS PARA loadUserByUsername()
    public Usuario findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USUARIO_NAO_ENCONTRADO_MSG, email)));
    }

    public Usuario getUsuarioByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(authentication.getPrincipal().toString());
    }

    public void bloqueiaUsuario(Integer id) {
        Usuario usuario = findEntityById(id);
        usuario.setStatusDeBloqueio(true);
        repository.save(usuario);
    }

}