package br.com.techlead.repository;

import br.com.techlead.domain.PenalidadeUsuario;
import br.com.techlead.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenalidadeUsuarioRepository extends JpaRepository<PenalidadeUsuario, Integer> {
    Boolean existsByUsuario(Usuario usuario);
}