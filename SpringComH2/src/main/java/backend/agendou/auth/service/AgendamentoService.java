package backend.agendou.auth.service;

import backend.agendou.auth.dto.request.AgendamentoRequestDTO;
import backend.agendou.auth.mapper.AgendamentoMapper;
import backend.agendou.auth.model.Agendamento;
import backend.agendou.auth.repository.AgendamentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {
    private final AgendamentoRepository repository;
    private final AgendamentoMapper mapper;

    public AgendamentoService(AgendamentoRepository repository, AgendamentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ResponseEntity<String> marcarHorario(AgendamentoRequestDTO agendamentoRequest) {
        try {
            Optional<Agendamento> agendamentoExistente = repository.findByDataHoraCorte(agendamentoRequest.getDataHoraCorte());
            if (agendamentoExistente.isPresent()) {
                return ResponseEntity.status(409).body("Horário já agendado");
            }
            Agendamento agendamento = mapper.toEntity(agendamentoRequest);
            repository.save(agendamento);
            return ResponseEntity.status(201).body("Agendamento concluído");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocorreu um erro ao agendar");
        }
    }

    public ResponseEntity<List<Agendamento>> listarAgendamentos(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio.isAfter(fim)) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Agendamento> agendamentos = repository.findByDataHoraCorteBetween(inicio, fim);
        if (agendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(agendamentos);
    }

    public ResponseEntity<String> atualizarAgendamento(Integer id, AgendamentoRequestDTO agendamentoRequest) {
        try {
            Optional<Agendamento> agendamentoExistente = repository.findById(id);
            if (agendamentoExistente.isEmpty()) {
                return ResponseEntity.status(404).body("Agendamento não encontrado");
            }
            Agendamento agendamento = agendamentoExistente.get();
            agendamento.setProfissional(agendamentoRequest.getProfissional());
            agendamento.setDataHoraCorte(agendamentoRequest.getDataHoraCorte());
            repository.save(agendamento);
            return ResponseEntity.status(200).body("Agendamento atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocorreu um erro ao atualizar o agendamento");
        }
    }

    public ResponseEntity<String> removerAgendamento(Integer id) {
        try {
            if (!repository.existsById(id)) {
                return ResponseEntity.status(404).body("Agendamento não encontrado");
            }
            repository.deleteById(id);
            return ResponseEntity.status(200).body("Agendamento deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocorreu um erro ao deletar o agendamento");
        }
    }
}
