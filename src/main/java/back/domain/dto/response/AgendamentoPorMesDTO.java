package back.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgendamentoPorMesDTO {
    private String mes;
    private Long totalAgendamentos;
}
