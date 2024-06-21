package med.voll.api.domain.consulta;




import med.voll.api.domain.consulta.desafio.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidationDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    @Autowired
    private List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;


    public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgendarConsulta){

        if(!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new ValidationDeIntegridad("Este id para el paciente no fue encontrado");
        }
        if (datosAgendarConsulta.idMedico()!=null&&!medicoRepository
                .existsById(datosAgendarConsulta.idMedico())) {
            throw new ValidationDeIntegridad("Este id para el medico no fue encontrado");
        }


        validadores.forEach(v -> v.validar(datosAgendarConsulta));

        var paciente=pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();

        var medico = seleccionarMedico(datosAgendarConsulta);

        if(medico==null){
            throw new ValidationDeIntegridad("no existen medicos disponibles para " +
                    "este horario y especialidad");
        }

        Consulta consulta = new Consulta(medico,paciente,datosAgendarConsulta.fecha());
        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    public void cancelar(DatosCancelamientoConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidationDeIntegridad("Id de la consulta informado no existe!");
        }

        validadoresCancelamiento.forEach(v -> v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {

        if(datosAgendarConsulta.idMedico()!=null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }


        if(datosAgendarConsulta.especialidad()==null){
             throw new ValidationDeIntegridad(" debe seleccionarse una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoEspecialidadEnFecha(datosAgendarConsulta.especialidad(),
        datosAgendarConsulta.fecha());
    }
    public Page<DatosDetalleConsulta> consultar(Pageable paginacion) {
        return consultaRepository.findAll(paginacion).map(DatosDetalleConsulta::new);
    }
}
