package med.voll.api.controller;//package med.voll.api.controller;


import jakarta.validation.Valid;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/consultas")

public class ConsultaController {


    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional

public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta) {
        System.out.println(datosAgendarConsulta);

        service.agendar(datosAgendarConsulta);
    return ResponseEntity.ok(new DatosDetalleConsulta(null, null, null, null));
}


}
