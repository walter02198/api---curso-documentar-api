package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorDeConsultas {

    @Autowired
    private PacienteRepository repository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idPaciente()==null) {
            throw new ValidationException("El paciente no se encuentra activo");
        }

        var pacienteActivo=repository.findActivoById(datosAgendarConsulta.idPaciente());

        if(!pacienteActivo){
            throw new ValidationException("No se pueden agendar citas con pacientes " +
                    "inactivos en el sistema");
        }
    }
}
