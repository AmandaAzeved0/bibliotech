package br.com.techlead.service;

import br.com.techlead.domain.EstoqueLivro;
import br.com.techlead.dto.request.LivroPostRequestDto;

import br.com.techlead.repository.EstoqueLivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstoqueService {
    @Autowired
    EstoqueLivroRepository repository;

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
}
