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

    private final String BASE_URL = "/biblioteca/api/v1";
    private final List<String> ADMIN_ROUTES = Arrays.asList(
            HttpMethod.POST.name() +BASE_URL+ "/livro",
            HttpMethod.GET.name() +BASE_URL+ "/livro",
            HttpMethod.DELETE.name() + BASE_URL + "/livro/{id}",
            HttpMethod.GET.name() +BASE_URL+"/livro/lista",
            HttpMethod.GET.name() +BASE_URL+"/livro/lista/livros",
            HttpMethod.GET.name() +BASE_URL+"/livro/lista/{id}", //lista por estoque id
            HttpMethod.PUT.name() +BASE_URL+ "/livro/{id}}",
            HttpMethod.DELETE.name() +BASE_URL+"/livro/delete/all",

            HttpMethod.POST.name() +BASE_URL+"/cadastro/bibliotecario",

            HttpMethod.POST.name() +BASE_URL+"/usuario/mudar-senha",
            HttpMethod.GET.name() + BASE_URL+"/usuario",

            HttpMethod.GET.name() +BASE_URL+"/estoque",
            HttpMethod.GET.name() +BASE_URL+"/estoque/lista/generos",
            HttpMethod.DELETE.name() +BASE_URL+"/estoque/delete/{id}",

            HttpMethod.POST.name() +BASE_URL+"/solicitacaoDeEmprestimo",
            HttpMethod.PUT.name() +BASE_URL+"/solicitacaoDeEmprestimo",
            HttpMethod.GET.name() +BASE_URL+"/solicitacaoDeEmprestimo/lista"
    );

    private final List<String> BIBLIOTECARIO_ROUTES = Arrays.asList(
            HttpMethod.GET.name() +BASE_URL+"/solicitacaoDeEmprestimo/lista",
            HttpMethod.GET.name() +BASE_URL+"/solicitacaoDeEmprestimo/{id}",
            HttpMethod.PUT.name() +BASE_URL+"/solicitacaoDeEmprestimo",

            HttpMethod.PUT.name() +BASE_URL+"/emprestimos/{id}",

            HttpMethod.POST.name() +BASE_URL+"/usuario/mudar-senha",
            HttpMethod.GET.name() + BASE_URL+"/usuario",

            HttpMethod.GET.name() +BASE_URL+"/estoque"
    );

    private final List<String> CLIENTE_ROUTES = Arrays.asList(
            HttpMethod.POST.name() +BASE_URL+ "/livros",
            HttpMethod.PUT.name() +BASE_URL+ "/livros/{id}",
            HttpMethod.DELETE.name() +BASE_URL+ "/livros/{id}",
            HttpMethod.POST.name() +BASE_URL+"/solicitacaoDeEmprestimo",
            HttpMethod.GET.name() +BASE_URL+"/livros",
            HttpMethod.GET.name() + BASE_URL+"/usuario",
            HttpMethod.POST.name() +BASE_URL+"/usuario/mudar-senha"
    );

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;


        if (request.getRequestURI().contains("cadastro/cliente")
                || request.getRequestURI().contains("autenticar")
                || request.getRequestURI().contains("reset-senha")) {
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
        // Separa a URI em partes usando o caractere '/'
        String[] uriParts = uri.split("/");

        // Remove qualquer valor que seja um número inteiro positivo, pois isso seria o ID
        for (int i = 0; i < uriParts.length; i++) {
            if (uriParts[i].matches("[1-9][0-9]*")) {
                uriParts[i] = "{id}";
            }
        }
        // Reconstroi a URI sem o valor do ID
        String sanitizedUri = String.join("/", uriParts);
        String sanitizedUriComRequestParam = sanitizedUri.replace("/"+uriParts[uriParts.length-1], "");

        if (perfil.equals("ADM") && (ADMIN_ROUTES.contains(method + sanitizedUri)|| ADMIN_ROUTES.contains(method + sanitizedUriComRequestParam))){
            return true;
        } else if (perfil.equals("USR") && CLIENTE_ROUTES.contains(method + sanitizedUri)) {
            return true;
        } else if (perfil.equals("BIB") && BIBLIOTECARIO_ROUTES.contains(method + sanitizedUri)) {
            return true;
        } else if(sanitizedUri.equals(BASE_URL+"/cadastro/cliente")||sanitizedUri.equals(BASE_URL+"/autenticar")){
            return true;
        }else {
            return false;
        }
    }

}
