package br.com.techlead.security;

import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.LoginDto;
import br.com.techlead.exception.FalhaNaAutenticacaoException;
import br.com.techlead.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final SenhaEnconder senhaEnconder;
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    private final UsuarioService userService;

    private static void responseText(HttpServletResponse response, String content) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        response.setContentLength(bytes.length);
        response.setStatus(200);
        response.getOutputStream().write(bytes);
        response.flushBuffer();
    }

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            ApplicationContext ctx,
            SenhaEnconder senhaEnconder,
            UsuarioService usuarioService, UsuarioService userService) {
        this.authenticationManager = authenticationManager;
        this.senhaEnconder = senhaEnconder;
        this.usuarioService = usuarioService;
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest req,
                                                final HttpServletResponse res) throws AuthenticationException {
        try {

            if(isPreflight(req)){
                res.setStatus(HttpServletResponse.SC_NO_CONTENT);
                return null;
            }
            LoginDto creds = new ObjectMapper()
                    .readValue(req.getInputStream(), LoginDto.class);

            log.info("Authentication with email: {}", creds.getEmail());
            Usuario user = usuarioService.findByEmail(creds.getEmail());

            if (user == null) throw new UsernameNotFoundException("Usuário não encontrado.");

            if (!user.isEnabled()) {
                throw new DisabledException("Usuário desabilitado.");
            }

            if (!senhaEnconder.bCryptPasswordEncoder().matches(creds.getSenha(), user.getSenha())) {
                throw new BadCredentialsException("Credenciais inválidas.");
            }


            return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                creds.getEmail(),
                                creds.getSenha(),
                                new ArrayList<>())
            );
        } catch (Exception e) {
            responseText(res, new FalhaNaAutenticacaoException().getMessage());
            log.warn("Falha na autenticação");
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        String email = ((Usuario) auth.getPrincipal()).getUsername();


        Usuario usuario = userService.findByEmail(email);

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", auth.getAuthorities());

        String token = Jwts.builder()
                .setSubject(usuario.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityUtil.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityUtil.getTokenSecret() )
                .addClaims(claims)
                .compact();

        ServletOutputStream stream = res.getOutputStream();
        String perfil = "";
        Collection<SimpleGrantedAuthority> auths = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
        for (SimpleGrantedAuthority granted: auths) {
           perfil= granted.getAuthority();
        }
        res.addHeader(SecurityUtil.HEADER_STRING, SecurityUtil.TOKEN_PREFIX + token);
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedOutputStream out = new BufferedOutputStream(stream)) {
            out.write(mapper.writeValueAsString(token).getBytes());
        }

        res.addHeader("UserID", usuario.getUsername());
    }

    private boolean isPreflight(HttpServletRequest request) {
        return "OPTIONS".equals(request.getMethod());
    }
}
