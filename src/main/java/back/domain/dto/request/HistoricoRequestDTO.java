package back.domain.dto.request;

import back.domain.dto.response.AgendamentoSimplificadoResponseDTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoRequestDTO {

    private LocalDateTime data;
    private Integer idAgendamento;
    private String statusAnterior;
    private String statusAtual;
    private AgendamentoSimplificadoResponseDTO agendamento;
    private Integer id;
    private String nomeUsuario;
    private String nomeFuncionario;
    private String nomeServico;

    public HistoricoRequestDTO(Integer id, String statusAnterior, String statusAtual) {
    }
}
