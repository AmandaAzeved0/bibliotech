package br.com.techlead.controller;


import br.com.techlead.service.EmprestimoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/emprestimo")
@RestController
@RequiredArgsConstructor
@Slf4j
@Api("emprestimo")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    @PutMapping("/devolucao")
    @ApiOperation("Devolução de livro")
    public ResponseEntity<Void> devolverLivro(@RequestParam Integer emprestimoId ,Boolean perda, Boolean dano) throws NotFoundException {
        emprestimoService.devolverLivro(emprestimoId,perda,dano);
        return ResponseEntity.ok().build();
    }
}