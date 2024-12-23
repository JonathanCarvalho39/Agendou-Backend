package back.api.config.security;

import back.domain.model.Empresa;
import back.domain.model.Usuario;
import back.domain.repository.EmpresaRepository;
import back.domain.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    EmpresaRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            var login = tokenService.validateToken(token);

            Optional<Usuario> usuarioOptional = userRepository.findByEmail(login);
            Optional<Empresa> empresaOptional = repository.findByEmail(login);

            if (usuarioOptional.isPresent()) {
                var usuario = usuarioOptional.get();
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else if (empresaOptional.isPresent()) {
                var empresa = empresaOptional.get();
                var authentication = new UsernamePasswordAuthenticationToken(empresa, null, empresa.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                throw new RuntimeException("Usuário ou Empresa não encontrados");
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
