package br.com.techlead.controller;

import br.com.techlead.dto.response.EstoqueResponseDto;
import br.com.techlead.service.EstoqueService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/estoque")
@RestController
@RequiredArgsConstructor
@Slf4j
@Api("estoque")
public class EstoqueController {
    private final EstoqueService estoqueService;
    @GetMapping()
    public List<EstoqueResponseDto> findAll() {
        return estoqueService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        estoqueService.delete(id);
    }

    @GetMapping("/lista/generos")
    public List<String> findAllGeneros() {
        return estoqueService.findAllGeneros();
    }

}
