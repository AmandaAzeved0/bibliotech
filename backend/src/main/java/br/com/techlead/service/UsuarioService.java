package br.com.techlead.service;

import br.com.techlead.domain.Emprestimo;
import br.com.techlead.domain.Livro;
import br.com.techlead.domain.SolicitacaoDeEmprestimo;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.request.MudarSenhaRequestDto;
import br.com.techlead.dto.response.EmprestimoResponseDto;
import br.com.techlead.dto.response.LivroResponseDto;
import br.com.techlead.dto.response.SolicitacoesDeEmprestimoResponseDto;
import br.com.techlead.dto.response.UsuarioResponseDto;
import br.com.techlead.exception.BadRequestException;
import br.com.techlead.mapper.emprestimoMapper.EmprestimoMapper;
import br.com.techlead.mapper.livroMapper.LivroMapper;
import br.com.techlead.mapper.solicitacaoDeEmprestimoMapper.SolicitacaoDeEmprestimoMapper;
import br.com.techlead.mapper.usuarioMapper.UsuarioMapper;
import br.com.techlead.repository.EmprestimoRepository;
import br.com.techlead.repository.LivroRepository;
import br.com.techlead.repository.SolicitacaoDeEmprestimoRepository;
import br.com.techlead.repository.UsuarioRepository;
import br.com.techlead.security.SecurityUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailService emailService;

    private final EmprestimoRepository emprestimoRepository;

    private final LivroRepository livroRepository;
    private final SolicitacaoDeEmprestimoRepository solicitacaoDeEmprestimoRespository;
    private static final String USUARIO_NAO_ENCONTRADO_MSG = "Usuário com email %s não encontrado！";



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USUARIO_NAO_ENCONTRADO_MSG, email)));
    }



    public Usuario save(Usuario entity) {
        repository.findByEmail(entity.getEmail()).ifPresent(usuario -> {
            throw new BadRequestException("Email já cadastrado");
        });
        String senhaEncript = bCryptPasswordEncoder.encode(entity.getSenha());
        entity.setSenha(senhaEncript);
        entity.setStatusDeBloqueio(false);
        return repository.save(entity);


    }

    public Usuario findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USUARIO_NAO_ENCONTRADO_MSG, email)));
    }

    public Usuario getUsuarioByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(authentication.getPrincipal().toString());
    }



    public UsuarioResponseDto getUsuarioResponseDtoByToken() {
        Usuario usuario = getUsuarioByToken();
        if(usuario.getPerfil().getSigla().equals("ADM") || usuario.getPerfil().getSigla().equals("BIB")) {
            return this.listarTodasAsTransacoes(usuario);
        }
        List<LivroResponseDto> livrosCadasrtados = this.buscaLivrosCadastradosDoUsuario(usuario) ;
        List<EmprestimoResponseDto> emprestimos = this.buscaEmprestimosDoUsuario(usuario);
        List<SolicitacoesDeEmprestimoResponseDto> solicitacoesDeEmprestimo = this.buscaSolicitacoesDeEmprestimoDoUsuario(usuario);
        return UsuarioMapper.INSTANCE.toDto(usuario, emprestimos, livrosCadasrtados, solicitacoesDeEmprestimo);
    }

    //metodo pra retornar usuario com todos os livros, emprestimos e soliciatoces de emprestimo
    private UsuarioResponseDto listarTodasAsTransacoes(Usuario usuario) {
        List<LivroResponseDto> livrosCadasrtados = this.getAllLivros(livroRepository.findAll());
        List<EmprestimoResponseDto> emprestimos = this.getAllEmprestimos(emprestimoRepository.findAll());
        List<SolicitacoesDeEmprestimoResponseDto> solicitacoesDeEmprestimo = this.getAllSolicitacoes(solicitacaoDeEmprestimoRespository.findAll());
        return UsuarioMapper.INSTANCE.toDto(usuario, emprestimos, livrosCadasrtados, solicitacoesDeEmprestimo);
    }

    private List<LivroResponseDto> getAllLivros(List<Livro> livros) {
        List<LivroResponseDto> dtos = new ArrayList<>();
        livros.forEach(livro -> {
            dtos.add(LivroMapper.INSTANCE.toDto(livro));
        });
        return dtos;
    }


    public void bloqueiaUsuario(Integer id) {
        Usuario usuario = findEntityById(id);
        usuario.setStatusDeBloqueio(true);
        repository.save(usuario);
    }

    public List<EmprestimoResponseDto> getAllEmprestimos(List<Emprestimo> emprestimos) {
        List<EmprestimoResponseDto> emprestimoResponseDtos = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos) {
            emprestimoResponseDtos.add(EmprestimoMapper.INSTANCE.toDto(emprestimo));
        }
        return emprestimoResponseDtos;
    }

    public List<SolicitacoesDeEmprestimoResponseDto> getAllSolicitacoes(List<SolicitacaoDeEmprestimo> solicitacoes) {
        List<SolicitacoesDeEmprestimoResponseDto> dtos = new ArrayList<>();
        solicitacoes.forEach(solicitacao ->
                dtos.add(SolicitacaoDeEmprestimoMapper.INSTANCE.toDto(solicitacao))
        );
        return dtos;
    }
    private List<SolicitacoesDeEmprestimoResponseDto> buscaSolicitacoesDeEmprestimoDoUsuario(Usuario usuario) {
        return usuario.getReservas().stream()
                .map(SolicitacaoDeEmprestimoMapper.INSTANCE::toDto)
                .collect(Collectors.toList());

    }

    private List<EmprestimoResponseDto> buscaEmprestimosDoUsuario(Usuario usuario) {
        return usuario.getEmprestimos().stream()
                .map(EmprestimoMapper.INSTANCE::toDto)
                .collect(Collectors.toList());

    }

    private List<LivroResponseDto> buscaLivrosCadastradosDoUsuario(Usuario usuario) {
        return usuario.getLivros().stream()
                .map(LivroMapper.INSTANCE::toDto)
                .collect(Collectors.toList());

    }


    public void desbloqueiaUsuario(Integer id) {
        Usuario usuario = findEntityById(id);
        usuario.setStatusDeBloqueio(false);
        repository.save(usuario);
    }


    private String gerarTokenUsuario(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", usuario.getAuthorities());

        String compact = Jwts.builder()
                .setSubject(usuario.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityUtil.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, usuario.getSenha())
                .addClaims(claims)
                .compact();
        return compact;
    }

    private Usuario findEntityById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado！"));
    }

    public void enviaSenhaProvisoria(String email) {
        Usuario usuario = this.findByEmail(email);
        String novaSenha = SecurityUtil.gerarSenhaAleatoria();
        usuario.setSenha(bCryptPasswordEncoder.encode(novaSenha));
        usuario.setSenhaProvisoriaAtiva(true);
        repository.save(usuario);
        emailService.enviaEmailComSenhaProvisoria(email, novaSenha);
    }

    public void alteraSenha(MudarSenhaRequestDto mudarSenhaRequestDto){
        Usuario usuario = this.getUsuarioByToken();
        this.verificaSenhaNovaEConfirmacaoIguais(mudarSenhaRequestDto.getSenhaNova(), mudarSenhaRequestDto.getSenhaNovaConfirmacao());
        this.verificaSenhaAtual(mudarSenhaRequestDto.getSenhaAtual(),usuario);
        usuario.setSenha(bCryptPasswordEncoder.encode(mudarSenhaRequestDto.getSenhaNova()));
        usuario.setSenhaProvisoriaAtiva(false);
        repository.save(usuario);
    }

    private void verificaSenhaAtual(String senhaAtual, Usuario usuario) {

        if(!bCryptPasswordEncoder.matches(senhaAtual, usuario.getSenha())) throw new BadRequestException("Senha atual não confere");

    }

    private void verificaSenhaNovaEConfirmacaoIguais(String novaSenha, String senhaConfirmacao) throws BadRequestException {
        if(!novaSenha.equals(senhaConfirmacao)) throw new BadRequestException("Senha nova e confirmação não são iguais");

    }
}