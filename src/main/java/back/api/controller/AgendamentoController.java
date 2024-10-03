package back.api.controller;

import back.domain.dto.request.AgendamentoRequestDTO;
import back.domain.model.Agendamento;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import back.service.service.AgendamentoService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Api(value = "API de agendamentos", tags = {"Agendamento"})
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @ApiOperation(value = "Marcar horário", notes = "Marca um novo horário de agendamento")
    @PostMapping()
    public ResponseEntity<String> marcarHorario(
            @ApiParam(value = "Dados do agendamento", required = true)
            @RequestBody @Valid AgendamentoRequestDTO agendamento) {
        return service.marcarHorario(agendamento);
    }

    @ApiOperation(value = "Atualizar agendamento", notes = "Atualiza um agendamento existente")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAgendamento(
            @ApiParam(value = "ID do agendamento", required = true)
            @PathVariable Integer id,
            @ApiParam(value = "Dados atualizados do agendamento", required = true)
            @RequestBody @Valid AgendamentoRequestDTO agendamento) {
        return service.atualizarAgendamento(id, agendamento);
    }

    @ApiOperation(value = "Remover agendamento", notes = "Remove um agendamento existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerAgendamento(
            @ApiParam(value = "ID do agendamento", required = true)
            @PathVariable Integer id) {
        return service.removerAgendamento(id);
    }

    @ApiOperation(value = "Listar agendamentos", notes = "Lista todos os agendamentos em um intervalo de tempo")
    @GetMapping()
    public ResponseEntity<List<Agendamento>> listarAgendamentos(
            @ApiParam(value = "Data e hora de início", required = true)
            @RequestParam(name = "inicio") LocalDateTime inicio,
            @ApiParam(value = "Data e hora de fim", required = true)
            @RequestParam(name = "fim") LocalDateTime fim) {
        return service.listarAgendamentos(inicio, fim);
    }
}