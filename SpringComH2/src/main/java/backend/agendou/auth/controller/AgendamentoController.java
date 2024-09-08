package backend.agendou.auth.controller;

import backend.agendou.auth.dto.request.AgendamentoRequestDTO;
import backend.agendou.auth.model.Agendamento;
import backend.agendou.auth.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    @Autowired
    private AgendamentoService service;

    @PostMapping()
    public ResponseEntity<String> marcarHorario(@RequestBody @Valid AgendamentoRequestDTO agendamento) {
        return service.marcarHorario(agendamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAgendamento(@PathVariable Integer id, @RequestBody @Valid AgendamentoRequestDTO agendamento) {
        return service.atualizarAgendamento(id, agendamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerAgendamento(@PathVariable Integer id) {
        return service.removerAgendamento(id);
    }

    @GetMapping()
    public ResponseEntity<List<Agendamento>> listarAgendamentos(
            @RequestParam(name = "inicio") LocalDateTime inicio,
            @RequestParam(name = "fim") LocalDateTime fim) {
        return service.listarAgendamentos(inicio, fim);
    }
}
