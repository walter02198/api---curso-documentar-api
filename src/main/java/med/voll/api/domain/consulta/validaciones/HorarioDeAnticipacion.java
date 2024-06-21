package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas {

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {
    var ahora= LocalDateTime.now();
    var horadeConsulta= datosAgendarConsulta.fecha();
    var diferenciade30minutos= Duration.between(ahora,horadeConsulta).toMinutes()<30;
    if(diferenciade30minutos){
        throw new ValidationException("La consulta no puede ser agendada en menos de 30 minutos");
    }
    }
}
