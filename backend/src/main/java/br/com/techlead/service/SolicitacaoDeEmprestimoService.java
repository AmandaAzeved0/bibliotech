package br.com.techlead.service;

import br.com.techlead.domain.Livro;
import br.com.techlead.domain.SolicitacaoDeEmprestimo;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.request.SolicitacaoDeEmprestimoPostRequestDto;
import br.com.techlead.dto.request.SolicitacaoDeemprestimoPutResquestDto;
import br.com.techlead.dto.response.SolicitacoesDeEmprestimoResponseDto;
import br.com.techlead.enums.StatusSolicitacaoEmprestimoEnum;
import br.com.techlead.exception.BadRequestException;
import br.com.techlead.mapper.solicitacaoDeEmprestimoMapper.SolicitacaoDeEmprestimoMapper;
import br.com.techlead.repository.SolicitacaoDeEmprestimoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SolicitacaoDeEmprestimoService {
    private final SolicitacaoDeEmprestimoRepository repository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;
    private final EmprestimoService emprestimoService;
    private final PenalidadeUsuarioService penalidadeUsuarioService;

    public void save(SolicitacaoDeEmprestimoPostRequestDto reservaDto) {
        livroService.verificaDisponibilidade(reservaDto.getLivroId());
        Usuario usuario = usuarioService.getUsuarioByToken();
        penalidadeUsuarioService.verificaUsuarioPenalizado(usuario);

        Livro livro = livroService.findEnityById(reservaDto.getLivroId());
        LocalDate dataSoliciacao = LocalDate.now();

        SolicitacaoDeEmprestimo solicitacaoDeEmprestimo = SolicitacaoDeEmprestimoMapper
                .INSTANCE.toEntity(
                        usuario,
                        dataSoliciacao,
                        StatusSolicitacaoEmprestimoEnum.PENDENTE.name(),
                        livro,
                        reservaDto.getQuantidadeDias());

        repository.save(solicitacaoDeEmprestimo);
    }

    public SolicitacaoDeEmprestimo findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Livro não encontrado！"));
    }

    public Page<SolicitacoesDeEmprestimoResponseDto> findAllComFiltro(
            LocalDate data,
            Integer usuarioId,
            Integer livroId,
            StatusSolicitacaoEmprestimoEnum status,
            Pageable pageable) {

        Page<SolicitacaoDeEmprestimo> entityPage = repository
                .findAllComFiltro(data, usuarioId, livroId, String.valueOf(status), pageable);

        List<SolicitacoesDeEmprestimoResponseDto> dtos = this.getSolicitacoesDeEmprestimoResponseDtos(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    private List<SolicitacoesDeEmprestimoResponseDto> getSolicitacoesDeEmprestimoResponseDtos(List<SolicitacaoDeEmprestimo> solicitacoes) {
        List<SolicitacoesDeEmprestimoResponseDto> dtos = new ArrayList<>();
        solicitacoes.forEach(solicitacao ->
            dtos.add(SolicitacaoDeEmprestimoMapper.INSTANCE.toDto(solicitacao))
        );
        return dtos;
    }
    public void update(SolicitacaoDeemprestimoPutResquestDto solicitacaoDeemprestimoPutResquestDto) {
        SolicitacaoDeEmprestimo entity = this.findById(solicitacaoDeemprestimoPutResquestDto.getSolicitacaoEmprestimoId());
        livroService.verificaDisponibilidade(entity.getLivro().getId());

        if(solicitacaoDeemprestimoPutResquestDto.getStatus().name().equals("APROVADO")) {
            livroService.updateDisponibilidade(entity.getLivro().getId());
            emprestimoService.save(entity.getLivro(), entity.getQuantidadeDias(), entity.getUsuario());
        }

        entity.setStatus(solicitacaoDeemprestimoPutResquestDto.getStatus().name());
        repository.save(entity);
    }


    public List<SolicitacoesDeEmprestimoResponseDto> findAll() {
        List<SolicitacaoDeEmprestimo> solicitacoes = repository.findAll();
        return this.getSolicitacoesDeEmprestimoResponseDtos(solicitacoes);
    }
}