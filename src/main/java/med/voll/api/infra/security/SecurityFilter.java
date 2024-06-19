package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");// este es el encabezado de autorización y el nombre siempre es "Authorization"
        if(authHeader!=null ){
            var token=authHeader.replace("Bearer ", "");
            var subject=tokenService.getSubject(token);
            if(subject!=null){
                //token valido
                var usuario= usuarioRepository.findByLogin(subject);

                var authentication=new UsernamePasswordAuthenticationToken(usuario,null,
                        usuario.getAuthorities());//Forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);//se utiliza para ejecutar el siguiente filtro en la cadena de
                                                // filtros y permitir que procese la solicitud HTTP entrante.
    }

}
