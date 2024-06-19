package med.voll.api.controller;


import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequestMapping("/medicos")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping ()
    public ResponseEntity<DatosRespuestaMedicos>
    registrarMedicos(@RequestBody @Valid DatosRegistrosMedicos datosRegistrosMedicos,
                                           UriComponentsBuilder uriComponentsBuilder) {
        Medico medico=medicoRepository.save(new Medico(datosRegistrosMedicos));
        DatosRespuestaMedicos datosRespuestaMedicos = new DatosRespuestaMedicos(medico.getId(), medico.getNombre(),
                medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(),
                        medico.getDireccion().getComplemento(),medico.getDireccion().getNumero()));
        URI url=uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedicos);
        //Return 201 created
        //URL donde encontrar al medico
        //GET http://localhost:8080/medicos/xx
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedicos>> listadoMedicos(@PageableDefault(size = 20) Pageable paginacion) { //10 por defecto Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedicos::new);
        return ResponseEntity.ok().body(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedicos::new));

    }
    @PutMapping
    @Transactional
    public ResponseEntity actualizaMedicos(@RequestBody @Valid DatosActualizarMedicos datosActualizarMedicos){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedicos.id());
        medico.actualizarDatos(datosActualizarMedicos);
        return ResponseEntity.ok(new DatosRespuestaMedicos(medico.getId(), medico.getNombre(),
                medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(),
                        medico.getDireccion().getComplemento(),medico.getDireccion().getNumero())));

//aca usamos el transactional porque estamos usando jpa puro(sin metodo save) a diferencia de registrarMedicos
        //que estamos usando el Repository
    }
    //Delete logico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedicos(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();


//Delete en base de datos
//        public void eliminarMedicos(@PathVariable Long id){
//            Medico medico = medicoRepository.getReferenceById(id);
//            medicoRepository.delete(medico);
    }
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity RetornarDatosMedicos(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        var datosMedicos = new DatosRespuestaMedicos(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getComplemento(), medico.getDireccion().getNumero()));
        return ResponseEntity.ok(datosMedicos);
    }
}
