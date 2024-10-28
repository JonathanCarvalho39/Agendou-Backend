package back.service.service;


import back.domain.dto.request.ComercianteRequestDTO;
import back.domain.dto.response.ComercianteResponseDTO;
import back.domain.mapper.ComercianteMapper;
import back.domain.model.Comerciante;
import back.domain.repository.ComercianteRepository;
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