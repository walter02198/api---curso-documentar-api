package med.voll.api.domain.medico;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistrosMedicos(
        @NotBlank//colocamos notblank porque esta anotacion adentro contiene notnull
        String nombre,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")//esto nos indica que la validacion sera un numero de 4 a 6 digitos
        String documento,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefono,
        @NotNull
        Especialidad especialidad,//aca especialidad es un enum asi que tb le colocamos notnull
        @NotNull
        @Valid
        DatosDireccion direccion) {//aca colacomo notnull porque esto es un objeto
                                    // de DatosDireccion y a los objetos le llega notnull
}
