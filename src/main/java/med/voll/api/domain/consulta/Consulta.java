package med.voll.api.domain.consulta;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name="consultas")
@Entity(name="Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime fecha;

    @Column(name = "motivo_cancelamiento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamiento motivoCancelamiento;

    //Aca creamos un constructor ya que nosostros dentro del servicio de AgendaDeConsultaService(en la linea 58)
    //estabamos pasando el id como nulo y tendriamos que agregar tb el motivo de cancelamiento como nulo
    //al crear este constructor solamente pasamos los datos que queremos sin agregar nulls innnecesarios
    //en nuestra clase AgendaDeConsultaService

    public Consulta( Medico medico, Paciente paciente, LocalDateTime fecha) {
        this.medico=medico;
        this.paciente=paciente;
        this.fecha=fecha;
    }


    public void cancelar(MotivoCancelamiento motivo) {
        this.motivoCancelamiento = motivo;
    }
    }





