package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario,
                                      LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndFecha(Long medicoId, LocalDateTime fecha);

}
