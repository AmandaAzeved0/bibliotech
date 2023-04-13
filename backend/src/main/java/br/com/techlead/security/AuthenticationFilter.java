package br.com.techlead.security;

import br.com.techlead.config.SpringApplicationContext;
import br.com.techlead.domain.Usuario;
import br.com.techlead.dto.request.UsuarioCadastroRequestDto;
import br.com.techlead.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.*;
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;




    @Override
    public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res)
            throws AuthenticationException {
        res.setHeader("Origin", "http://localhost:4200");
        try {
            if (isPreflight(req)) {
                res.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                UsuarioCadastroRequestDto creds = new ObjectMapper().readValue(req.getInputStream(), UsuarioCadastroRequestDto.class);
                logger.info("Authentication with email: {}", creds.getEmail());

                return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>()));
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String userName = ((Usuario) auth.getPrincipal()).getUsername();

        logger.info("AUTHENTICATED USER: {} ", userName);

        UsuarioRepository usuarioRepository = (UsuarioRepository) SpringApplicationContext.getBean("usuarioRepository");

        Optional<Usuario> optional = usuarioRepository.findByEmail(userName);

        if (!optional.isPresent()) {
            logger.error("Erro ao recuperar usuario da base");
            throw new RuntimeException("Usuario inexistente");
        }

        Usuario usuario = optional.get();

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", auth.getAuthorities());

        String token = Jwts.builder()
                .setSubject(usuario.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityUtil.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityUtil.getTokenSecret() )
                .addClaims(claims)
                .compact();

        ServletOutputStream stream = res.getOutputStream();

        List<String> perfis = new ArrayList<String>();
        auth.getAuthorities().forEach(perfil -> perfis.add(perfil.toString()));


        ObjectMapper mapper = new ObjectMapper();

        try (BufferedOutputStream out = new BufferedOutputStream(stream)) {
            out.write(mapper.writeValueAsString(token).getBytes());
        }

    }

    private boolean isPreflight(HttpServletRequest request) {

        return "OPTIONS".equals(request.getMethod());
    }

}
