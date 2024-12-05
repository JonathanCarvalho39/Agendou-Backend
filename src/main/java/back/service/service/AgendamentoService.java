package back.service.service;

import back.domain.dto.request.AgendamentoRequestDTO;
import back.domain.dto.response.AgendamentoResponseDTO;
import back.domain.mapper.AgendamentoMapper;
import back.domain.model.Agendamento;
import back.domain.model.Funcionario;
import back.domain.model.Servico;
import back.domain.model.Usuario;
import back.domain.repository.AgendamentoRepository;
import back.domain.repository.FuncionarioRepository;
import back.domain.repository.ServicoRepository;
import back.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final AgendamentoMapper mapper;
    private final ServicoRepository servicoRepository;
    private final FuncionarioRepository funcionarioRepository;

    private static final Logger logger = LoggerFactory.getLogger(AgendamentoService.class);
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<?> agendar(AgendamentoRequestDTO agendamentoRequest) {

        if (repository.findByData(agendamentoRequest.getData()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horário já agendado");
        }

        Agendamento agendamento = mapper.toEntity(agendamentoRequest);
        Agendamento agendamentoSalvo = repository.save(agendamento);

        AgendamentoResponseDTO responseDTO = mapper.toAgendamentoResponseDto(agendamentoSalvo);

        if (responseDTO == null) {
            logger.error("Falha ao cadastrar o Agendamento");
            return ResponseEntity.status(400).build();
        }

        logger.info("Agendamento cadastrado com sucesso: " + responseDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    public List<AgendamentoResponseDTO> listarAgendamentosPorFuncionario(Integer funcionarioId) {
        List<Agendamento> agendamentos = repository.findAllByFkFuncionarioId(funcionarioId);

        // Convert to DTO
        return agendamentos.stream()
                .map(mapper::toAgendamentoResponseDto)
                .collect(Collectors.toList());
    }

    public List<AgendamentoResponseDTO> listarAgendamentos() {
        List<Agendamento> agendamentos = repository.findAll();
        return agendamentos.stream()
                .map(mapper::toAgendamentoResponseDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> buscarAgendamentoPorId(Integer id) {
        Optional<Agendamento> agendamentoExistente = repository.findById(id);

        if (agendamentoExistente.isEmpty()) {
            logger.error("Agendamento com id " + id + " não encontrado");
            return ResponseEntity.status(404).body("Agendamento não encontrado");
        }

        Agendamento agendamento = agendamentoExistente.get();
        AgendamentoResponseDTO responseDTO = mapper.toAgendamentoResponseDto(agendamento);

        return ResponseEntity.status(200).body(responseDTO);
    }

    public ResponseEntity<?> atualizarAgendamento(Integer id, AgendamentoRequestDTO agendamentoRequest) {
        Optional<Agendamento> agendamentoExistente = repository.findById(id);

        if (agendamentoExistente.isEmpty()) {
            logger.error("Falha ao atualizar o agendamento");
            return ResponseEntity.status(404).body("Agendamento não encontrado");
        }

        Agendamento agendamento = agendamentoExistente.get();

        agendamento.setData(agendamentoRequest.getData());

        Optional<Servico> servico = servicoRepository.findById(agendamentoRequest.getFkServico());
        if (servico.isEmpty()) {
            return ResponseEntity.status(400).body("Serviço não encontrado.");
        }
        agendamento.setFkServico(servico.get());

        Optional<Usuario> usuario = usuarioRepository.findById(agendamentoRequest.getFkUsuario());
        if (usuario.isEmpty()) {
            return ResponseEntity.status(400).body("Usuário não encontrado.");
        }
        agendamento.setFkUsuario(usuario.get());

        Optional<Funcionario> funcionario = funcionarioRepository.findById(agendamentoRequest.getFkFuncionario());
        if (funcionario.isEmpty()) {
            return ResponseEntity.status(400).body("Funcionário não encontrado.");
        }
        agendamento.setFkFuncionario(funcionario.get());

        repository.save(agendamento);

        return ResponseEntity.status(200).body(mapper.toAgendamentoResponseDto(agendamento));
    }


    public ResponseEntity<?> removerAgendamento(Integer id) {
        Optional<Agendamento> agendamentoExistente = repository.findById(id);

        if (agendamentoExistente.isEmpty()) {
            logger.error("Falha ao deletar o agendamento");
            return ResponseEntity.status(404).body("Agendamento não encontrado.");
        }

        Agendamento agendamento = agendamentoExistente.get();
        logger.info("Agendamento deletado com sucesso: " + agendamento.getData());
        repository.delete(agendamento);

        return ResponseEntity.status(200).body(agendamento);
    }
}
