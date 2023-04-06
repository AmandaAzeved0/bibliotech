package br.com.techlead.repository;

import br.com.techlead.domain.SolicitacaoDeEmprestimo;
import br.com.techlead.enums.StatusSolicitacaoEmprestimoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SolicitacaoDeEmprestimoRepository extends JpaRepository<SolicitacaoDeEmprestimo, Integer> {
    @Query("SELECT s FROM SolicitacaoDeEmprestimo s WHERE " +
            "(:data is null or s.dataSoliciacao = :data) " +
            "AND (:usuarioId is null or s.usuario.id = :usuarioId) " +
            "AND (:livroId is null or s.livro.id = :livroId) " +
            "AND (:status is null or s.status = :status)")
    Page<SolicitacaoDeEmprestimo> findAllComFiltro(
            @Param("data")LocalDate data,
            @Param("usuarioId")Integer usuarioId,
            @Param("livroId")Integer livroId,
            @Param("status") String status,
            Pageable pageable);
}