package br.com.techlead.service;

import br.com.techlead.domain.Emprestimo;
import br.com.techlead.domain.Livro;
import br.com.techlead.domain.Usuario;
import br.com.techlead.exception.BadRequestException;
import br.com.techlead.mapper.emprestimoMapper.EmprestimoMapper;
import br.com.techlead.repository.EmprestimoRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmprestimoService {

    private final EmprestimoRepository repository;
    private final PenalidadeUsuarioService penalidadeService;

    private final LivroService livroService;



    public void save(Livro livro, Integer quantidadeDias, Usuario usuario) {
        LocalDate dataEmprestimoInicio = LocalDate.now();
        LocalDate dataEmprestimoFim = dataEmprestimoInicio.plusDays(quantidadeDias);
        Emprestimo emprestimo = EmprestimoMapper.INSTANCE.toEntity(usuario, dataEmprestimoInicio, dataEmprestimoFim, livro);
        repository.save(emprestimo);
    }

    public void devolverLivro(Integer emprestimoId,Boolean perda, Boolean dano) {
        Emprestimo emprestimo = this.findById(emprestimoId);
        emprestimo.setDataDevolucao(LocalDate.now());
        if(perda || dano) {
            //todo atualiza estado do livro

            penalidadeService.aplicaPenalidadePerdaOuDano(emprestimo);
            return;
        }else if(emprestimo.getDataDevolucao().isAfter(emprestimo.getDataEmprestimoFim())) {
            penalidadeService.aplicaPenalidadeAtraso(emprestimo);
        }
        livroService.updateDisponibilidade(emprestimo.getLivro().getId());
        repository.save(emprestimo);
    }


    private Emprestimo findById(Integer emprestimoId) {
        return repository.findById(emprestimoId).orElseThrow(() -> new BadRequestException("Emprestimo não encontrado！"));
    }

}