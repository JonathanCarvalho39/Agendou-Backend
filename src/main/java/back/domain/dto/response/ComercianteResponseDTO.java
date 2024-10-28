package back.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercianteResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
}