package back.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendamentoResponseDTO {
        private Integer id;
        private String profissional;
        private LocalDateTime dataHoraCorte;
}
