package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private MedicoRepository repositorio;

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {


        var medicoConConsulta = repository.existsByMedicoIdAndFecha(
                datosAgendarConsulta.idMedico(),
                datosAgendarConsulta.fecha());
        if (medicoConConsulta) {
            throw new ValidationException("El medico ya tiene una consulta agendada para ese dia");
        }


    }
}
