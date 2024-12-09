package back.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoricoResponseDTO {

    private LocalDateTime data;
    private Integer idAgendamento;
    private String statusAnterior;
    private String statusAtual;
    private AgendamentoSimplificadoResponseDTO agendamento;
    private Integer id;
    private String nomeUsuario;
    private String nomeFuncionario;
    private String nomeServico;

    public HistoricoResponseDTO(Integer id, LocalDateTime data, String statusAnterior, String statusAtual, AgendamentoSimplificadoResponseDTO agendamentoSimplificado) {
    }
}
