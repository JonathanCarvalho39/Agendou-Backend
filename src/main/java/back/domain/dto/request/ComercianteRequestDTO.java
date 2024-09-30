package back.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercianteRequestDTO {
    private String nome;
    private String email;
    private String telefone;
}