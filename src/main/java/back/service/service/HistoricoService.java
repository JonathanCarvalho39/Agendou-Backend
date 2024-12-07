package back.service.service;

import back.domain.dto.request.HistoricoRequestDTO;
import back.domain.dto.response.AgendamentoSimplificadoResponseDTO;
import back.domain.dto.response.HistoricoResponseDTO;
import back.domain.mapper.AgendamentoMapper;
import back.domain.mapper.HistoricoMapper;
import back.domain.model.Agendamento;
import back.domain.model.HistoricoAgendamento;
import back.domain.repository.AgendamentoRepository;
import back.domain.repository.HistoricoRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoService {

    private final HistoricoRepository repository;
    private final HistoricoMapper mapper;
    private final AgendamentoRepository agendamentoRepository;

    public HistoricoService(HistoricoRepository historicoRepository, HistoricoMapper historicoMapper, AgendamentoRepository agendamentoRepository) {
        this.repository = historicoRepository;
        this.mapper = historicoMapper;
        this.agendamentoRepository = agendamentoRepository;
    }

    public HistoricoAgendamento salvarHistorico(HistoricoRequestDTO historicoRequest) {
        Agendamento agendamento = agendamentoRepository.findById(historicoRequest.getIdAgendamento())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        HistoricoAgendamento historico = new HistoricoAgendamento();
        historico.setNomeUsuario(historico.getNomeUsuario());
        historico.setNomeServico(historico.getNomeServico());
        historico.setNomeFuncionario(historico.getNomeFuncionario());
        historico.setStatusAnterior(historicoRequest.getStatusAnterior());
        historico.setStatusAtual(historicoRequest.getStatusAtual());
        historico.setData(LocalDateTime.now());

        return repository.save(historico);
    }

    public HistoricoAgendamento listarHistoricoPorAgendamento(Integer idAgendamento) {
        return repository.findById(idAgendamento).orElseThrow(() ->
                new RuntimeException("Histórico não encontrado para o agendamento de ID: " + idAgendamento));
    }


    public List<HistoricoResponseDTO> obterHistoricoPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<HistoricoAgendamento> historicoList = repository.findByDataBetween(dataInicio, dataFim);
        return historicoList.stream()
                .map(mapper::toHistoricoResponseDto)
                .collect(Collectors.toList());
    }

    public List<HistoricoResponseDTO> obterHistoricoPorStatus(String status) {
        List<HistoricoAgendamento> historicoList = repository.findByStatusAtual(status);
        return historicoList.stream()
                .map(mapper::toHistoricoResponseDto)
                .collect(Collectors.toList());
    }

    public List<HistoricoResponseDTO> obterTodoHistorico() {
        List<HistoricoAgendamento> historicos = repository.findAll();

        return historicos.stream()
                .map(historico -> {
                    AgendamentoSimplificadoResponseDTO agendamentoSimplificado = new AgendamentoSimplificadoResponseDTO(
                            historico.getNomeUsuario(),
                            historico.getData()
                    );

                    return new HistoricoResponseDTO(
                            historico.getId(),
                            historico.getData(),
                            historico.getStatusAnterior(),
                            historico.getStatusAtual(),
                            agendamentoSimplificado
                    );
                })
                .collect(Collectors.toList());
    }


    public List<HistoricoResponseDTO> listarAgendamentosFuturos(LocalDateTime dataInicio) {
        LocalDateTime agora = LocalDateTime.now();

        if (dataInicio.isBefore(agora)) {
            throw new IllegalArgumentException("Data inválida: não é permitido consultar datas anteriores ao momento atual.");
        }

        List<HistoricoAgendamento> agendamentosFuturos = repository.findByDataAfter(dataInicio);

        return agendamentosFuturos.stream()
                .map(mapper::toHistoricoResponseDto)
                .collect(Collectors.toList());
    }

    public List<HistoricoResponseDTO> listarAgendamentosPassados(LocalDateTime dataInicio) {
        LocalDateTime agora = LocalDateTime.now();

        if (dataInicio.isAfter(agora)) {
            throw new IllegalArgumentException("Data inválida: a data inicial não pode ser no futuro.");
        }

        List<HistoricoAgendamento> agendamentosPassados = repository.findByDataBetween(dataInicio, agora);

        return agendamentosPassados.stream()
                .map(mapper::toHistoricoResponseDto)
                .collect(Collectors.toList());
    }

    public List<String> buscarUsuariosAtivos(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return repository.findActiveUsers(dataInicio, dataFim);
    }

    public byte[] getHistoricoCsv(LocalDateTime dataInicio, LocalDateTime dataFim) throws IOException {

        List<HistoricoAgendamento> historicoAgendamentos = repository.findByDataBetween(dataInicio, dataFim);
        List<HistoricoResponseDTO> historicoResponseDTOS = historicoAgendamentos.stream()
                .map(servico -> {
                    try {
                        System.out.println("Data início: " + dataInicio + ", Data fim: " + dataFim);
                        System.out.println("Quantidade de registros encontrados: " + historicoAgendamentos.size());

                        return mapper.toHistoricoResponseDto(servico);
                    } catch (Exception e) {
                        throw new RuntimeException("Erro ao mapear HistoricoAgendamento para HistoricoRespondeDTO", e);
                    }
                })
                .toList();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {

            writer.write("Data;StatusAnterior;StatusAtual;Agendamento\n");

            for (HistoricoResponseDTO dto : historicoResponseDTOS) {
                writer.write(String.format("%s;%s;%s;%s\n",
                        dto.getData(),
                        dto.getStatusAnterior(),
                        dto.getStatusAtual(),
                        dto.getAgendamento() != null ? dto.getAgendamento().toString() : ""));
            }

            writer.flush();
            System.out.println(new String(outputStream.toByteArray(), StandardCharsets.UTF_8));
            return outputStream.toByteArray();
        }
    }

}