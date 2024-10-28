package back.api.controller;

import back.domain.dto.request.EmpresaRequestDTO;
import back.domain.dto.response.EmpresaResponseDTO;
import back.service.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@AllArgsConstructor
@Validated
public class EmpresaController {

    private final EmpresaService service;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        System.out.println("Recebida requisição de login com email: " + email);
        return service.login(email, senha);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EmpresaResponseDTO>> listarEmpresas() {
        return ResponseEntity.status(200).body(service.listarEmpresas());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody @Valid EmpresaRequestDTO empresa) {
        return service.cadastrarEmpresa(empresa);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarEmpresa(@RequestBody @Valid EmpresaRequestDTO empresa, @PathVariable Integer id){
        return service.atualizarEmpresa(id, empresa);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarEmpresa(@PathVariable Integer id){
        return service.deletarEmpresa(id);
    }
}
