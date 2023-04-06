package br.com.techlead.controller;

import br.com.techlead.dto.request.UsuarioCadastroRequestDto;
import br.com.techlead.service.CadastroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cadastro")
@RestController
@RequiredArgsConstructor
@Slf4j
@Api("cadastro")
public class CadastroUsuarioController {

    private final CadastroService service;

    @PostMapping
    @ApiOperation("Salva um usuário")
    public ResponseEntity<String> save(@RequestBody @Validated UsuarioCadastroRequestDto usuarioCadastroRequestDto) {
        service.save(usuarioCadastroRequestDto);
        return ResponseEntity.ok().build();
    }
}
