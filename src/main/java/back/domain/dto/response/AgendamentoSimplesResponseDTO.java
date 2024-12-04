package back.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendamentoSimplesResponseDTO {
    private Integer id;
    private LocalDateTime data;
    private String funcionario;
    private String usuario;
    private String servico;
}
