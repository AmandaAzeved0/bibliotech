package br.com.techlead.repository;

import br.com.techlead.domain.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.hibernate.dialect.PostgreSQLDialect.*;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    List<Livro> findByDisponivelTrue();
    List<Livro> findByEstoqueId(Integer estoqueId);

    @Query("SELECT l FROM Livro l WHERE " +
            "(:titulo is null or LOWER(l.estoque.titulo) LIKE LOWER(CONCAT('%',CAST(:titulo AS string),'%'))) " +
            "AND (:autor is null or LOWER(l.estoque.autor) LIKE LOWER(CONCAT('%',CAST(:autor AS string),'%'))) " +
            "AND (:disponivel is null or l.disponivel = :disponivel) " +
            "AND (:emprestado is null or l.disponivel = false ) " +
            "AND (:estoqueId is null or l.estoque.id = :estoqueId) " +
            "AND :estado is null or LOWER(l.estado) LIKE LOWER(CONCAT('%', CAST(:estado AS string),'%'))")
    Page<Livro> findAllComFiltro(
            @Param("titulo") String titulo,
            @Param("autor") String autor,
            @Param("disponivel") Boolean disponivel,
            @Param("emprestado") Boolean emprestado,
            @Param("estado") String estado,
            @Param("estoqueId") Integer estoqueId,
            Pageable pageable);

}