package contas.service.contas_service.config;

import contas.service.contas_service.service.AutenticacaoService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AutenticacaoFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);

    private final AutenticacaoService autenticacaoService;
    private final GerenciadorTokenJwt jwtTokenManager;

    // URLs liberadas (sem autenticação)
    private static final String[] URLS_PERMITIDAS = {
            "/swagger-ui/",
            "/swagger-ui.html",
            "/swagger-resources",
            "/swagger-resources/",
            "/configuration/ui",
            "/configuration/security",
            "/api/public/",
            "/api/public/authenticate",
            "/webjars/",
            "/v3/api-docs/",
            "/actuator/",
            "/h2-console/",
            "/error/",
            "/clientes/cadastro/pf",
            "/clientes/cadastro/pj",
            "/clientes/login",
            "/clientes/login/"
    };

    public AutenticacaoFilter(AutenticacaoService autenticacaoService, GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoService = autenticacaoService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // Pula autenticação para URLs públicas
        for (String urlPermitida : URLS_PERMITIDAS) {
            if (path.startsWith(urlPermitida)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String username = null;
        String jwtToken = null;
        String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenManager.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException exception) {
                LOGGER.info("[FALHA AUTENTICACAO] - Token expirado, usuario: {} - {}",
                        exception.getClaims().getSubject(), exception.getMessage());
                LOGGER.trace("[FALHA AUTENTICACAO] - stack trace: %s", exception);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return; // interrompe o filtro, não segue adiante
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            addUsernameInContext(request, username, jwtToken);
        }

        filterChain.doFilter(request, response);
    }

    private void addUsernameInContext(HttpServletRequest request, String username, String jwtToken) {

        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        if (jwtTokenManager.validateToken(jwtToken, userDetails)) {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
