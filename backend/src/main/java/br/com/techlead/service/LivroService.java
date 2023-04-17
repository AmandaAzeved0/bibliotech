package br.com.techlead.service;

import br.com.techlead.domain.EstoqueLivro;
import br.com.techlead.domain.Livro;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.request.LivroPostRequestDto;
import br.com.techlead.dto.request.LivroPutRequestDto;
import br.com.techlead.dto.response.LivroResponseDto;
import br.com.techlead.exception.BadRequestException;
import br.com.techlead.mapper.livroMapper.LivroMapper;
import br.com.techlead.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    private final EstoqueService estoqueService;

    private final UsuarioService usuarioService;

    
    public void save(LivroPostRequestDto livroPostRequestDto) {
        estoqueService.atualizaEstoqueCasoExistaOuCriaNovo(livroPostRequestDto);
        EstoqueLivro estoque =  estoqueService.findByIsbn(livroPostRequestDto.getTitulo());
        Usuario usuario = usuarioService.getUsuarioByToken();
        this.salvaLivrosPorQuantidadeEntidade(livroPostRequestDto, estoque, usuario);
    }

    private void salvaLivrosPorQuantidadeEntidade(LivroPostRequestDto livroPostRequestDto, EstoqueLivro estoque, Usuario usuario) {
        for (int i = 0; i < livroPostRequestDto.getQuantidade(); i++) {
            Livro livroEntity = LivroMapper.INSTANCE.toEntity(livroPostRequestDto, estoque, usuario);
            repository.save(livroEntity);
        }
    }

    public void deleteById(Integer id) {
        estoqueService.atualizaQuantidade(this.findById(id).getEstoqueId(), -1);
        repository.deleteById(id);
    }

    public LivroResponseDto findById(Integer id) {
        return LivroMapper.INSTANCE.toDto(this.findEnityById(id));
    }

    public Livro findEnityById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Livro não encontrado！"));
    }


    public Page<LivroResponseDto> findALLComFiltro(
            String titulo,
            String autor,
            Boolean disponivel,
            Boolean emprestado,
            String estado,
            Integer estoqueId,
            Pageable pageable) {
        Page<Livro> entityPage = repository.findAllComFiltro(
                        titulo,
                        autor,
                        disponivel,
                        emprestado,
                estado,
                estoqueId,
                        pageable);
        List<Livro> entities = entityPage.getContent();
        List<LivroResponseDto> dtos = this.getLivros(entities);
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    private List<LivroResponseDto> getLivros(List<Livro> entities) {
        List<LivroResponseDto> dtos = new ArrayList<>();
        entities.forEach(livro -> {
            dtos.add(LivroMapper.INSTANCE.toDto(livro));
        });
        return dtos;
    }

    public String update(LivroPutRequestDto livroDto, Integer id) {
        Livro data = repository.findById(id).get();
        data.setEstado(livroDto.getEstado());
        repository.save(data);
        return "OK!";
    }

    public void deleteAll() {
        repository.deleteAll();
        estoqueService.deleteAll();
    }

    public void updateDisponibilidade(Integer id) {
        Livro livro = repository.findById(id).get();
        Boolean disponivel = !livro.getDisponivel();
        livro.setDisponivel(disponivel);
        repository.save(livro);
    }

    public void verificaDisponibilidade(Integer livroId) {
        LivroResponseDto livro = this.findById(livroId);
        if (!livro.getDisponivel()) {
            throw new BadRequestException("Livro não disponível");
        }

    }

    public List<LivroResponseDto> findAll() {
        List<Livro> livros = repository.findAll();
        return this.getLivros(livros);
    }


    public List<LivroResponseDto> findByEstoqueId(Integer id) {
        List<Livro> livros = repository.findByEstoqueId(id);
        return this.getLivros(livros);
    }

    public List<LivroResponseDto> findLivrosDisponiveisByGenero(String genero) {
        List<Livro> livros = repository.findDisponiveisByGenero(genero);
        return this.getLivros(livros);
    }


}