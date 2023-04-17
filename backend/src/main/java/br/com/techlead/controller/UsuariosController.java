package br.com.techlead.controller;

import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.request.MudarSenhaRequestDto;
import br.com.techlead.dto.response.LivroResponseDto;
import br.com.techlead.dto.response.UsuarioResponseDto;
import br.com.techlead.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/usuario")
@RestController
@RequiredArgsConstructor
@Slf4j
@Api("usuario")
public class UsuariosController {

    private final UsuarioService usuarioService;
    @GetMapping()
    @ApiOperation(value = "Retorna um Usuario pelo token", response = LivroResponseDto.class)
    public ResponseEntity<UsuarioResponseDto> findBytoken() {
        UsuarioResponseDto usuario = usuarioService.getUsuarioResponseDtoByToken();
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/reset-senha")
    @ApiOperation(value = "Envia senha provisória por email", response = String.class)
    public ResponseEntity<HttpStatus> enviaSenhaProvisoria(@RequestParam @Validated String email) {
        usuarioService.enviaSenhaProvisoria(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/mudar-senha")
    @ApiOperation(value = "Altera senha do usuário", response = String.class)
    public ResponseEntity<HttpStatus> alteraSenha(@RequestBody @Validated MudarSenhaRequestDto mudarSenhaRequestDto) {
        usuarioService.alteraSenha(mudarSenhaRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
