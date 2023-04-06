package br.com.techlead.controller;


import br.com.techlead.dto.request.LivroPostRequestDto;
import br.com.techlead.dto.request.LivroPutRequestDto;
import br.com.techlead.dto.response.LivroResponseDto;
import br.com.techlead.service.LivroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path="/livro" ,  produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Slf4j
@Api("livro")
public class LivroController {
    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    @ApiOperation("Salva um livro")
    public ResponseEntity<Void> save(@RequestBody @Validated List<LivroPostRequestDto> livroPostRequestDto) {
        livroService.save(livroPostRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna um livro pelo id", response = LivroResponseDto.class)
    public ResponseEntity<LivroResponseDto> findById(@PathVariable("id") Integer id) {
        LivroResponseDto livro = livroService.findById(id);
        return ResponseEntity.ok(livro);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um livro pelo id")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
      livroService.findById(id);
        livroService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/lista")
    @ApiOperation(value = "Retorna uma lista de livros com ou sem filtro", response = LivroResponseDto.class)
    public ResponseEntity<Page<LivroResponseDto>> findAllComFiltroOpcional(
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "autor", required = false) String autor,
            @RequestParam(value = "disponivel", required = false) Boolean disponivel,
            @RequestParam(value = "emprestado", required = false) Boolean emprestado,
            @RequestParam(value = "estoqueId", required = false) Integer estoqueId,
            @RequestParam(value = "estado", required = false) String estado,
            Pageable pageable) {
        Page<LivroResponseDto> livroPage = livroService.findALLComFiltro(
                titulo, autor, disponivel, emprestado, estado, estoqueId,
                pageable);
        return ResponseEntity.ok(livroPage);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza o estado de um livro pelo id")
    public ResponseEntity<Void> atualizaEstado(@RequestBody @Validated LivroPutRequestDto livroDto, @PathVariable("id") Integer id) {
        livroService.update(livroDto, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/all")
    @ApiOperation(value = "Deleta todos os livros e limpa o estoque")
    public ResponseEntity<Void> deleteAll() {
        livroService.deleteAll();
        return ResponseEntity.ok().build();
    }
}