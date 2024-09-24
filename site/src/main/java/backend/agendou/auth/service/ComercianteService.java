package backend.agendou.auth.service;

import backend.agendou.auth.dto.request.ComercianteRequestDTO;
import backend.agendou.auth.dto.response.ComercianteResponseDTO;
import backend.agendou.auth.mapper.ComercianteMapper;
import backend.agendou.auth.model.Comerciante;
import backend.agendou.auth.repository.ComercianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComercianteService {

    @Autowired
    private ComercianteRepository comercianteRepository;

    @Autowired
    private ComercianteMapper comercianteMapper;

    public List<ComercianteResponseDTO> getAllComerciantes() {
        return comercianteRepository.findAll().stream()
                .map(comercianteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ComercianteResponseDTO getComercianteById(Integer id) {
        return comercianteRepository.findById(id)
                .map(comercianteMapper::toResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comerciante não encontrado com o id " + id));
    }

    public ComercianteResponseDTO createComerciante(ComercianteRequestDTO comercianteDTO) {
        Comerciante comerciante = comercianteMapper.toEntity(comercianteDTO);
        Comerciante savedComerciante = comercianteRepository.save(comerciante);
        return comercianteMapper.toResponseDTO(savedComerciante);
    }

    public ComercianteResponseDTO updateComerciante(Integer id, ComercianteRequestDTO comercianteDTO) {
        Comerciante comerciante = comercianteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comerciante não encontrado com o id " + id));
        comerciante.setNome(comercianteDTO.getNome());
        comerciante.setEmail(comercianteDTO.getEmail());
        comerciante.setTelefone(comercianteDTO.getTelefone());
        Comerciante updatedComerciante = comercianteRepository.save(comerciante);
        return comercianteMapper.toResponseDTO(updatedComerciante);
    }

    public void deleteComerciante(Integer id) {
        Comerciante comerciante = comercianteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comerciante não encontrado com o id " + id));
        comercianteRepository.delete(comerciante);
    }
}