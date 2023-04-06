package br.com.techlead.controller;

import br.com.techlead.dto.request.SolicitacaoDeEmprestimoPostRequestDto;
import br.com.techlead.dto.request.SolicitacaoDeemprestimoPutResquestDto;
import br.com.techlead.dto.response.SolicitacoesDeEmprestimoResponseDto;
import br.com.techlead.enums.StatusSolicitacaoEmprestimoEnum;
import br.com.techlead.service.SolicitacaoDeEmprestimoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/solicitacaoDeEmprestimo")
@RestController
@RequiredArgsConstructor
@Slf4j
@Api("Solicitação de empréstimo")
public class SolicitacaoDeEmprestimoController {
    private final SolicitacaoDeEmprestimoService solicitacaoDeEmprestimoService;

    @PostMapping()
    @ApiOperation("Salva uma solicitação de empréstimo")
    public ResponseEntity<Void> save(@RequestBody @Validated SolicitacaoDeEmprestimoPostRequestDto reservaDto) {
        solicitacaoDeEmprestimoService.save(reservaDto);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/lista")
    @ApiOperation(
            value = "Retorna uma lista de solicitações com ou sem filtro",
            response = SolicitacoesDeEmprestimoResponseDto.class)
    public ResponseEntity<Page<SolicitacoesDeEmprestimoResponseDto>> findAllComFiltroOpcional(
            @RequestParam(value = "data", required = false) LocalDate data,
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId,
            @RequestParam(value = "livroId", required = false) Integer livroId,
            @RequestParam(value = "status", required = false) StatusSolicitacaoEmprestimoEnum status,
            Pageable pageable) {

        Page<SolicitacoesDeEmprestimoResponseDto> reservaPage = solicitacaoDeEmprestimoService
                .findAllComFiltro(data, usuarioId, livroId, status, pageable);

        return ResponseEntity.ok(reservaPage);
    }

    @PutMapping
    @ApiOperation("Atualiza uma solicitação de empréstimo")
    public ResponseEntity<Void> update(@RequestBody SolicitacaoDeemprestimoPutResquestDto solicitacaoDeemprestimoPutResquestDto) {
        solicitacaoDeEmprestimoService.update(solicitacaoDeemprestimoPutResquestDto);
        return ResponseEntity.ok().build();
    }

}