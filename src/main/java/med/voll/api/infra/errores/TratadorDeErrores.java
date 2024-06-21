package med.voll.api.infra.errores;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        var errores =e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }
    //con este codigo de tratador de errores recibimos la excepcion en el insomnia o el postman
    //de nuestra clase de la dependencia validation de maven(error:No se pueden agendar citas con pacientes
    //inactivos en el sistema este mensaje se encuentra dentro del paquete validaciones en la clase pacienteActivo)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }

    //con este codigo de tratador de errores recibimos la excepcion en el insomnia o el postman
    //de nuestra clase de validation de integridad(error:el id para el paciente y para el medico no fue encontrado)
    @ExceptionHandler(ValidationDeIntegridad.class)
    public ResponseEntity errorHandlerValidacionesDeIntegridad(Exception e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }



    private record DatosErrorValidacion(String campo,String error){
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(),error.getDefaultMessage());
        }
    }
}
