package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRespuestaMedicos(
                                    Long id,
                                    String nombre,
                                    String documento,
                                    String telefono,
                                    String especialidad,
                                    @Valid
                                    DatosDireccion direccion) {
}
