package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {

        var primerHorario= datosAgendarConsulta.fecha().withHour(7);
        var ultimoHorario= datosAgendarConsulta.fecha().withHour(18);

        var pacienteConConsulta=repository.existsByPacienteIdAndFechaBetween(datosAgendarConsulta.idPaciente(),
                primerHorario,ultimoHorario);
        if (pacienteConConsulta) {
            throw new ValidationException("El paciente ya tiene una consulta agendada para ese dia");
        }
    }
}
