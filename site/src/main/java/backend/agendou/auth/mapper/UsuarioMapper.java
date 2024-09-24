package backend.agendou.auth.mapper;

import backend.agendou.auth.dto.request.UsuarioRequestDTO;
import backend.agendou.auth.dto.response.UsuarioResponseDTO;
import backend.agendou.auth.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO requestDTO) throws Exception {
        try {
            Usuario usuario = new Usuario();
            usuario.setEmail(requestDTO.getEmail());
            usuario.setNome(requestDTO.getNome());
            usuario.setSenha(requestDTO.getSenha());
            usuario.setTipo(requestDTO.getTipo());
            return usuario;
        } catch (Exception e) {
            throw new Exception("Erro ao mapear UsuarioRequestDTO para entidade: " + e.getMessage());
        }
    }


    public UsuarioResponseDTO toDTO(Usuario entity) {
        try {
            UsuarioResponseDTO dto = new UsuarioResponseDTO();
            dto.setId(entity.getId());
            dto.setNome(entity.getNome());
            dto.setEmail(entity.getEmail());
            dto.setSenha(entity.getSenha());
            dto.setTipo(entity.getTipo());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível mapear o usuário para o DTO.", e);
        }
    }
}