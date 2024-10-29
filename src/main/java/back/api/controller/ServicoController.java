package back.api.controller;

import back.domain.dto.request.ServicoRequestDTO;
import back.domain.model.Servico;
import back.domain.repository.ServicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import back.service.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {
    @Autowired
    private ServicoService service;
    private ServicoRepository repository;

    @Operation(summary = "Cadastro de serviço", description = "Cadastrar um novo serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço cadastrado"),
            @ApiResponse(responseCode = "400", description = "Erro no cadastro do serviço")
    })
    @PostMapping()
    public ResponseEntity<String> cadastrarServico(@RequestBody @Valid ServicoRequestDTO servico) {
        try {
            service.cadastrarServico(servico);
            return ResponseEntity.status(201).body("Serviço cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Ocorreu um erro durante o cadastro.");
        }
    }

    @Operation(summary = "Lista de serviços", description = "Lista todos os serviços")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviços listados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Servico.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao listar serviços")
    })
    @GetMapping()
    public ResponseEntity<List<Servico>> listarServicos() {
        try {
            return ResponseEntity.ok(service.listarServicos());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Atualizar serviço", description = "Atualizar um serviço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Serviço atualizado"),
            @ApiResponse(responseCode = "400", description = "Entrada inválida")
    })
    @PutMapping
    public ResponseEntity<String> atualizarServico(@RequestBody @Valid ServicoRequestDTO servico) {
        if (servico.getId() == null) {
            return ResponseEntity.status(400).body("O id do serviço é obrigatório.");
        } else {
            try {
                service.atualizarServico(servico);
                return ResponseEntity.status(202).body("Serviço atualizado com sucesso.");
            } catch (Exception e) {
                return ResponseEntity.status(400).body("Ocorreu um erro durante a atualização do serviço.");
            }
        }
    }

    @Operation(summary = "Deletar serviço", description = "Deletar um serviço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço deletado"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarServico(@PathVariable Integer id){
        return service.deletarServico(id);
    }
}