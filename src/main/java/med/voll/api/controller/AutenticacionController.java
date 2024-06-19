package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutenticacionUsuarios;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DatosJWTToken;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired

    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuarios
                                                    datosAutenticacionUsuarios) {

        Authentication authenticationToken =new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuarios.login(),
                datosAutenticacionUsuarios.clave());
        var usuarioAutenticado=authenticationManager.authenticate(authenticationToken);
        var JWTToken =tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTToken));
    }
}

/*
Este es un controlador REST en Java utilizando Spring Boot. Aquí está lo que hace cada parte del código:

@RestController: Esta es una anotación de Spring que indica que la clase es un controlador de
recursos RESTful.
@RequestMapping("/login"): Esta anotación indica que todas las solicitudes a la ruta "/login"
serán manejadas por este controlador.
@Autowired: Esta es una anotación de Spring que indica que los campos deben ser inyectados
automáticamente por el contenedor de Spring. En este caso, se inyecta AuthenticationManager y TokenService.
AuthenticationManager authenticationManager: Este es un objeto que se utiliza para autenticar a
los usuarios.
TokenService tokenService: Este es un objeto que se utiliza para generar tokens JWT.
@PostMapping: Esta anotación indica que este método manejará las solicitudes POST a la ruta "/login".
public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuarios
datosAutenticacionUsuarios): Este es el método que maneja las solicitudes POST. Toma un objeto
DatosAutenticacionUsuarios como entrada y realiza la autenticación del usuario.

La dependencia maven que maneja  Authentication es
 <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
 */