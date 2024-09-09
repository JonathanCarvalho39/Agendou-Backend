package backend.agendou.auth.mapper;

import backend.agendou.auth.dto.request.ComercianteRequestDTO;
import backend.agendou.auth.model.Comerciante;
import backend.agendou.auth.dto.response.ComercianteResponseDTO;
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