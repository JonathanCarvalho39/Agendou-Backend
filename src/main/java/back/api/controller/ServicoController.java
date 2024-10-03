package back.api.controller;

import back.domain.dto.request.ServicoRequestDTO;
import back.domain.model.Servico;
import back.domain.repository.ServicoRepository;
import back.service.service.ServicoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "API de Serviços", tags = {"Serviço"})
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService service;
    private ServicoRepository repository;

    @ApiOperation(value = "Cadastrar Serviço", notes = "Cadastra um novo serviço")
    @PostMapping()
    public ResponseEntity<String> cadastrarServico(
            @ApiParam(value = "Dados do serviço", required = true)
            @RequestBody @Valid ServicoRequestDTO servico) {
        try {
            service.cadastrarServico(servico);
            return ResponseEntity.status(201).body("Serviço cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Ocorreu um erro durante o cadastro.");
        }
    }

    @ApiOperation(value = "Listar Serviços", notes = "Lista todos os serviços")
    @GetMapping()
    public ResponseEntity<List<Servico>> listarServicos() {
        try {
            return ResponseEntity.ok(service.listarServicos());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @ApiOperation(value = "Atualizar Serviço", notes = "Atualiza um serviço existente")
    @PutMapping
    public ResponseEntity<String> atualizarServico(
            @ApiParam(value = "Dados atualizados do serviço", required = true)
            @RequestBody @Valid ServicoRequestDTO servico) {
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

    @ApiOperation(value = "Deletar Serviço", notes = "Deleta um serviço existente")
    @DeleteMapping
    public ResponseEntity<String> deletarServico(
            @ApiParam(value = "Dados do serviço", required = true)
            @RequestBody @Valid ServicoRequestDTO servico) {
        try {
            service.deletarServico(servico);
            return ResponseEntity.status(202).body("Serviço deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Ocorreu um erro durante a deleção do serviço.");
        }
    }
}