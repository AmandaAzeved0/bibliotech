package br.com.techlead.mapper.emprestimoMapper;

import br.com.techlead.domain.Emprestimo;
import br.com.techlead.domain.Livro;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.response.EmprestimoResponseDto;

import java.time.LocalDate;

public class EmprestimoMapperImpl extends EmprestimoMapper{

    @Override
    public Emprestimo toEntity(Usuario usuario, LocalDate dataInicio, LocalDate dataFim, Livro livro) {
        Emprestimo emprestimo = Emprestimo.builder()
                .usuario(usuario)
                .dataEmprestimoInicio(dataInicio)
                .dataEmprestimoFim(dataFim)
                .livro(livro)
                .build();
        return emprestimo;
    }

    @Override
    public EmprestimoResponseDto toDto(Emprestimo emprestimo) {
        EmprestimoResponseDto emprestimoResponseDto = EmprestimoResponseDto.builder()
                .idDoLivro(emprestimo.getLivro().getId())
                .tituloDoLivro(emprestimo.getLivro().getEstoque().getTitulo())
                .dataDeEmprestimoInicio(emprestimo.getDataEmprestimoInicio())
                .dataDeEmprestimoFim(emprestimo.getDataEmprestimoFim())
                .dataDeDevolucao(emprestimo.getDataDevolucao())
                .build();
        return emprestimoResponseDto;
    }
}
