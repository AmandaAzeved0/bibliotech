package br.com.techlead.controller;

import br.com.techlead.dto.request.UsuarioCadastroRequestDto;
import br.com.techlead.service.CadastroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cadastro")
@RestController
@RequiredArgsConstructor
@Slf4j
@Api("cadastro")
public class CadastroUsuarioController {

    private final CadastroService service;

    @CrossOrigin(origins = "*")
    @PostMapping("/cliente")
    @ApiOperation("Salva um cliente")
    public ResponseEntity<String> saveCliente(@RequestBody @Validated UsuarioCadastroRequestDto usuarioCadastroRequestDto) {
        service.save(usuarioCadastroRequestDto,2);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bibliotecario")
    @ApiOperation("Salva um bibliotecário")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<String> saveBibliotecario(@RequestBody @Validated UsuarioCadastroRequestDto usuarioCadastroRequestDto) {
        service.save(usuarioCadastroRequestDto,3);
        return ResponseEntity.ok().build();
    }
}
