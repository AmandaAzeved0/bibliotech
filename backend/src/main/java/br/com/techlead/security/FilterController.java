package br.com.techlead.security;


import io.jsonwebtoken.Claims;
import io.swagger.models.HttpMethod;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Order(2)
public class FilterController implements Filter {
    private final String BASE_URL=" /biblioteca/api/v1";

    private final List<String> ADMIN_ROUTES = Arrays.asList(
            HttpMethod.POST.name() +BASE_URL+ "/livros",
            HttpMethod.GET.name() +BASE_URL+ "/livros/{id}",
            HttpMethod.DELETE.name() +BASE_URL+ "/livros/{id}",
            HttpMethod.GET.name() +BASE_URL+"/livros/lista",
            HttpMethod.PUT.name() +BASE_URL+ "/livros/{id}",
            HttpMethod.DELETE.name() +BASE_URL+"/livros/delete/all",
            HttpMethod.POST.name() +BASE_URL+"/cadastro/bibliotecario"
    );

    private final List<String> BIBLIOTECARIO_ROUTES = Arrays.asList(
            HttpMethod.GET.name() +BASE_URL+"/solicitacaoDeEmprestimo/lista",
            HttpMethod.GET.name() +BASE_URL+"/solicitacaoDeEmprestimo/{id}",
            HttpMethod.PUT.name() +BASE_URL+"/solicitacaoDeEmprestimo/",
            HttpMethod.PUT.name() +BASE_URL+"/emprestimos/{id}"
    );

    private final List<String> CLIENTE_ROUTES = Arrays.asList(
            HttpMethod.POST.name() +BASE_URL+ "/livros",
            HttpMethod.PUT.name() +BASE_URL+ "/livros/{id}",
            HttpMethod.DELETE.name() +BASE_URL+ "/livros/{id}",
            HttpMethod.POST.name() +BASE_URL+"/solicitacaoDeEmprestimo",
            HttpMethod.GET.name() +BASE_URL+"/livros"
    );

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;


        if (request.getRequestURI().contains("cadastro/cliente")) {
            chain.doFilter(req, res);
            return;
        }


        HttpServletResponse response = (HttpServletResponse) res;
        String token = request.getHeader(SecurityUtil.HEADER_STRING);
        Claims claims = SecurityUtil.extractAuthorities(token);
        String perfil = SecurityUtil.getAuthority(claims);


        if (hasAccess(perfil, request.getMethod(), request.getRequestURI())) {
            chain.doFilter(req, res);
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value(), "Acesso negado");

        }
    }

    private boolean hasAccess(String perfil, String method, String uri ) {
        if (perfil.equals("Administrador") && ADMIN_ROUTES.contains(method + " " + uri)) {
            return true;
        } else if (perfil.equals("Cliente") && CLIENTE_ROUTES.contains(method + " " + uri)) {
            return true;
        } else if (perfil.equals("Bibliotecário") && BIBLIOTECARIO_ROUTES.contains(method + " " + uri)) {
            return true;
        } else if(uri.equals(BASE_URL+"/cadastro/cliente")){
            return true;
        }else {
            return false;
        }
    }

}
