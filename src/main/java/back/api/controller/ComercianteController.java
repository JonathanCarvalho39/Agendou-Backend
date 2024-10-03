package back.api.controller;

import back.domain.dto.request.ComercianteRequestDTO;
import back.domain.dto.response.ComercianteResponseDTO;
import back.domain.mapper.ComercianteMapper;
import back.domain.model.Comerciante;
import back.domain.repository.ComercianteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "API de Comerciantes", tags = {"Comerciante"})
@RequestMapping("/comerciantes")
public class ComercianteController {

    @Autowired
    private ComercianteRepository comercianteRepository;

    @Autowired
    private ComercianteMapper comercianteMapper;

    @ApiOperation(value = "Listar Comerciantes", notes = "Lista todos os comerciantes")
    @GetMapping
    public List<ComercianteResponseDTO> listarComerciantes() {
        return comercianteRepository.findAll().stream()
                .map(comercianteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Listar Comerciante por ID", notes = "Lista um comerciante pelo ID")
    @GetMapping("/{id}")
    public ComercianteResponseDTO listarComercianteById(
            @ApiParam(value = "ID do comerciante", required = true)
            @PathVariable Integer id) {
        return comercianteRepository.findById(id)
                .map(comercianteMapper::toResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comerciante não encontrado com o id " + id));
    }

    @ApiOperation(value = "Criar Comerciante", notes = "Cria um novo comerciante")
    @PostMapping
    public ComercianteResponseDTO criarComerciante(
            @ApiParam(value = "Dados do comerciante", required = true)
            @RequestBody ComercianteRequestDTO comercianteDTO) {
        Comerciante comerciante = comercianteMapper.toEntity(comercianteDTO);
        Comerciante savedComerciante = comercianteRepository.save(comerciante);
        return comercianteMapper.toResponseDTO(savedComerciante);
    }

    @ApiOperation(value = "Atualizar Comerciante", notes = "Atualiza um comerciante existente")
    @PutMapping("/{id}")
    public ComercianteResponseDTO atualizarComerciante(
            @ApiParam(value = "ID do comerciante", required = true)
            @PathVariable Integer id,
            @ApiParam(value = "Dados atualizados do comerciante", required = true)
            @RequestBody ComercianteRequestDTO comercianteDTO) {
        Comerciante comerciante = comercianteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comerciante não encontrado com o id " + id));
        comerciante.setNome(comercianteDTO.getNome());
        comerciante.setEmail(comercianteDTO.getEmail());
        comerciante.setTelefone(comercianteDTO.getTelefone());
        Comerciante updatedComerciante = comercianteRepository.save(comerciante);
        return comercianteMapper.toResponseDTO(updatedComerciante);
    }

    @ApiOperation(value = "Deletar Comerciante", notes = "Deleta um comerciante existente")
    @DeleteMapping("/{id}")
    public void deleteComerciante(
            @ApiParam(value = "ID do comerciante", required = true)
            @PathVariable Integer id) {
        Comerciante comerciante = comercianteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comerciante não encontrado com o id " + id));
        comercianteRepository.delete(comerciante);
    }
}