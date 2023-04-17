package br.com.techlead.repository;

import br.com.techlead.domain.EstoqueLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EstoqueLivroRepository extends JpaRepository<EstoqueLivro, Integer> {

    Optional<EstoqueLivro> findByTitulo(String isbn);


    Boolean existsByTitulo(String isbn);

}