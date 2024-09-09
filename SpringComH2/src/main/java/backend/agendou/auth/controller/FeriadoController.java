package backend.agendou.auth.controller;

import backend.agendou.auth.model.Feriado;
import backend.agendou.auth.service.FeriadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feriados")
public class FeriadoController {

    @Autowired
    private FeriadoService service;

    @GetMapping
    public ResponseEntity<List<Feriado>> listarFeriadosOrdenados(@RequestParam String pais, @RequestParam int ano){
        try {
            return ResponseEntity.ok(service.buscarFeriadosOrdenados(pais, ano).getBody());
        } catch (Exception e){
            return ResponseEntity.status(204).build();
        }
    }
}
