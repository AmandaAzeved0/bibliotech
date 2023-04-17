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
@CrossOrigin(origins = "http://localhost:4200")
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
    @ApiOperation("Salva um ou mais livros")
    public ResponseEntity<Void> save(@RequestBody @Validated LivroPostRequestDto livroPostRequestDto) {
        livroService.save(livroPostRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna um livro pelo id", response = LivroResponseDto.class)
    public ResponseEntity<LivroResponseDto> findById(@PathVariable("id") Integer id) {
        LivroResponseDto livro = livroService.findById(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping("/lista/{estoqueId}")
    @ApiOperation(value = "Retorna um livro pelo estoque", response = LivroResponseDto.class)
    public ResponseEntity<List<LivroResponseDto>> findByEtoqueId(@PathVariable("estoqueId") Integer id) {
        List<LivroResponseDto> livros = livroService.findByEstoqueId(id);
        return ResponseEntity.ok(livros);
    }




    @GetMapping("/lista")
    @ApiOperation(value = "Retorna uma lista de livros", response = LivroResponseDto.class)
    public ResponseEntity<List<LivroResponseDto>> findAll() {
        List<LivroResponseDto> livroPage = livroService.findAll();
        return ResponseEntity.ok(livroPage);
    }

    @GetMapping("/lista/livros/{genero}")
    @ApiOperation(value = "Retorna uma lista de livros por genero", response = LivroResponseDto.class)
    public ResponseEntity<List<LivroResponseDto>> findByGenero(@PathVariable("genero") String genero) {
        List<LivroResponseDto> livroPage = livroService.findLivrosDisponiveisByGenero(genero);
        return ResponseEntity.ok(livroPage);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza o estado de um livro pelo id")
    public ResponseEntity<Void> atualizaEstado(@RequestBody @Validated LivroPutRequestDto livroDto, @PathVariable("id") Integer id) {
        livroService.update(livroDto, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um livro pelo id")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        livroService.findById(id);
        livroService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/all")
    @ApiOperation(value = "Deleta todos os livros e limpa o estoque")
    public ResponseEntity<Void> deleteAll() {
        livroService.deleteAll();
        return ResponseEntity.ok().build();
    }
}