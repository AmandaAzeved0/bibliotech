package br.com.techlead.repository;

import br.com.techlead.domain.TiposDePenalidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TiposDePenalidadeRepository extends JpaRepository<TiposDePenalidade, Integer> {
    Optional<TiposDePenalidade> findByDescricao(String descricao);
}