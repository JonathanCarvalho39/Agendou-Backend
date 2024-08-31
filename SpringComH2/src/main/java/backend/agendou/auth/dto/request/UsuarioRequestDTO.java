package backend.agendou.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioRequestDTO {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
}
