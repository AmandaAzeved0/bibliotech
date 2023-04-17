package br.com.techlead.service;

import br.com.techlead.domain.EstoqueLivro;
import br.com.techlead.dto.request.LivroPostRequestDto;

import br.com.techlead.dto.response.EstoqueResponseDto;
import br.com.techlead.mapper.estoqueMapper.EstoqueMapper;
import br.com.techlead.repository.EstoqueLivroRepository;
import br.com.techlead.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    @Autowired
    EstoqueLivroRepository repository;
    private final LivroRepository livroRepository;


    public EstoqueLivro save(EstoqueLivro estoqueLivro) {
        EstoqueLivro save = repository.save(estoqueLivro);
        return save;
    }

    public EstoqueLivro findByIsbn(String isbn) {
        return repository.findByTitulo(isbn).orElseThrow(() -> new RuntimeException("Estoque não encontrado！"));
    }

    public void atualizaQuantidade(Integer estoqueId, Integer quantidade) {
        EstoqueLivro estoqueLivro = repository.findById(estoqueId).orElseThrow(() -> new RuntimeException("Estoque não encontrado！"));
        estoqueLivro.setQuantidade(estoqueLivro.getQuantidade() + quantidade);
        repository.save(estoqueLivro);
    }

    public void atualizaEstoqueCasoExistaOuCriaNovo(LivroPostRequestDto livro) {
        if (repository.existsByTitulo(livro.getTitulo())) {
            this.atualizaQuantidade(this.findByIsbn(livro.getTitulo()).getId(), livro.getQuantidade());
        } else {
            this.save(EstoqueLivro.builder()
                    .titulo(livro.getTitulo())
                    .autor(livro.getAutor())
                    .genero(livro.getGenero())
                    .quantidade(livro.getQuantidade())
                    .build());
        }
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<EstoqueResponseDto> findAll() {
        return repository.findAll().stream().map(EstoqueMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public void delete(Integer id) {
        if(this.findById(id) != null) {
            livroRepository.deleteAllDisponiveisByEstoqueId(id);
            repository.deleteById(id);
        }

    }

    private EstoqueLivro findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Estoque não encontrado！"));
    }


    public List<String> findAllGeneros() {
        return repository.findAll()
                .stream().map(EstoqueLivro::getGenero)
                .distinct().collect(Collectors.toList());
    }
}
