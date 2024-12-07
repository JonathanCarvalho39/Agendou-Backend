package back.api.controller;


import back.domain.dto.request.HistoricoRequestDTO;
import back.domain.dto.response.HistoricoResponseDTO;
import back.domain.mapper.HistoricoMapper;
import back.domain.model.HistoricoAgendamento;
import back.service.service.HistoricoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService service;

    @Autowired
    private HistoricoMapper mapper;

    @Operation(summary = "Salvar histórico", description = "Salvar histórico de agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Histórico cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar histórico")
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarHistorico(@RequestBody @Valid HistoricoRequestDTO historicoRequest) {
        HistoricoAgendamento historico = service.salvarHistorico(historicoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(historico);
    }

    @Operation(summary = "Obter histórico por período", description = "Obtém o histórico de agendamentos em um intervalo de tempo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico obtido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao obter histórico")
    })
    @GetMapping("/por-periodo")
    public ResponseEntity<List<HistoricoResponseDTO>> obterHistoricoPorPeriodo(@RequestParam("dataInicio") LocalDateTime dataInicio, @RequestParam("dataFim") LocalDateTime dataFim) {
        List<HistoricoResponseDTO> historico = service.obterHistoricoPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(historico);
    }

    @Operation(summary = "Obter histórico por status", description = "Obtém o histórico de agendamentos por status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico obtido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao obter histórico")
    })
    @GetMapping("/por-status")
    public ResponseEntity<List<HistoricoResponseDTO>> obterHistoricoPorStatus(@RequestParam("status") String status) {
        List<HistoricoResponseDTO> historico = service.obterHistoricoPorStatus(status);
        return ResponseEntity.ok(historico);
    }

    @Operation(summary = "Obter todo o histórico", description = "Obtém todo o histórico de agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico obtido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao obter histórico")
    })
    @GetMapping("/todos")
    public ResponseEntity<List<HistoricoResponseDTO>> obterTodoHistorico() {
        List<HistoricoResponseDTO> historico = service.obterTodoHistorico();
        return ResponseEntity.ok(historico);
    }

    @Operation(summary = "Listar agendamentos futuros", description = "Lista todos os agendamentos futuros a partir de uma data específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos obtidos com sucesso"),
            @ApiResponse(responseCode = "400", description = "Data inválida fornecida")
    })
    @PostMapping("/agendamentos-futuros")
    public ResponseEntity<List<HistoricoResponseDTO>> listarAgendamentosFuturos(@RequestBody LocalDateTime dataInicio) {
        List<HistoricoResponseDTO> agendamentosFuturos = service.listarAgendamentosFuturos(dataInicio);
        return ResponseEntity.ok(agendamentosFuturos);
    }

    @Operation(summary = "Listar histórico de agendamento", description = "Recupera o histórico de um agendamento pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Histórico não encontrado")
    })
    @GetMapping("/agendamento/{idAgendamento}")
    public ResponseEntity<HistoricoResponseDTO> listarHistoricoPorAgendamento(
            @PathVariable Integer idAgendamento) {

        HistoricoAgendamento historico = service.listarHistoricoPorAgendamento(idAgendamento);

        HistoricoResponseDTO responseDTO = mapper.toHistoricoResponseDto(historico);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Listar agendamentos passados", description = "Lista todos os agendamentos do passado desde uma data específica até o momento atual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos obtidos com sucesso"),
            @ApiResponse(responseCode = "400", description = "Data inválida fornecida")
    })
    @PostMapping("/agendamentos-passados")
    public ResponseEntity<List<HistoricoResponseDTO>> listarAgendamentosPassados(@RequestBody LocalDateTime dataInicio) {
        List<HistoricoResponseDTO> agendamentosPassados = service.listarAgendamentosPassados(dataInicio);
        return ResponseEntity.ok(agendamentosPassados);
    }

}