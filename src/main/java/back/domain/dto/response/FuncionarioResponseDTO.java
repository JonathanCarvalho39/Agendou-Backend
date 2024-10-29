package back.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuncionarioResponseDTO {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
}
