package back.domain.mapper;

import back.domain.dto.request.ComercianteRequestDTO;
import back.domain.dto.response.ComercianteResponseDTO;
import back.domain.model.Comerciante;
import org.springframework.stereotype.Component;

@Component
public class ComercianteMapper {

    public ComercianteResponseDTO toDTO(Comerciante comerciante) {
        return new ComercianteResponseDTO(comerciante.getId(), comerciante.getNome(), comerciante.getEmail(), comerciante.getTelefone());
    }

    public Comerciante toEntity(ComercianteRequestDTO comercianteDTO) {
        return new Comerciante(null, comercianteDTO.getNome(), comercianteDTO.getEmail(), comercianteDTO.getTelefone());
    }

    public ComercianteResponseDTO toResponseDTO(Comerciante comerciante) {
        return new ComercianteResponseDTO(comerciante.getId(), comerciante.getNome(), comerciante.getEmail(), comerciante.getTelefone());
    }
}