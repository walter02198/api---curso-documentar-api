package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deberia retornar nulo si el medico se encuentra en otra consulta a esa hora")
    void seleccionarMedicoEspecialidadEnFechaEscenario1() {

        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico =registrarMedico("Jose", "jose@jose", "123",
                Especialidad.ORTOPEDIA); ;
        var paciente = registrarPaciente("David", "david@david", "589");
        registrarConsulta(medico, paciente, proximoLunes10H);

        var medicoLibre = medicoRepository.seleccionarMedicoEspecialidadEnFecha(Especialidad.ORTOPEDIA,
                proximoLunes10H);

        assertThat(medicoLibre).isNull();


    }
    @Test
    @DisplayName("Deberia retornar un medico cuando realice la consulta en la base de datos para ese horario")
    void seleccionarMedicoEspecialidadEnFechaEscenario2() {

        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico =registrarMedico("Jose", "jose@jose", "123",
                Especialidad.ORTOPEDIA); ;

        var medicoLibre = medicoRepository.seleccionarMedicoEspecialidadEnFecha(Especialidad.ORTOPEDIA,
                proximoLunes10H);

        assertThat(medicoLibre).isEqualTo(medico);

    }



    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
    em.persist(new Consulta(null, medico, paciente, fecha, null));
    }
    private Medico registrarMedico(String nombre, String email, String documento,
                                   Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }
    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {

        return new DatosRegistroPaciente(
                nombre,
                email,
                "78056789",
                documento,
                datosDireccion());
    }

    private DatosRegistrosMedicos datosMedico (String nombre,String documento, String email,
                                               Especialidad especialidad){
        return new DatosRegistrosMedicos(
                    nombre,
                    documento,
                    email,
                    "78056789",
                    especialidad,
                    datosDireccion());
        }
    private DatosDireccion datosDireccion() {
        return new DatosDireccion("Av. Principal 123",
                "Santa Cruz",
                "Santa Cruz",
                "123",
                "12");
    }
}
