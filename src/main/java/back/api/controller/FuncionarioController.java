package back.api.controller;

import back.domain.dto.request.FuncionarioRequestDTO;
import back.domain.dto.response.FuncionarioResponseDTO;
import back.service.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@AllArgsConstructor
@Validated
public class FuncionarioController {

    private final FuncionarioService service;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        System.out.println("Recebida requisição de login com email: " + email);
        return service.login(email, senha);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<FuncionarioResponseDTO>> listarFuncionarios() {
        return ResponseEntity.status(200).body(service.listarFuncionarios());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarFuncionario(@RequestBody @Valid FuncionarioRequestDTO funcionario) {
        return service.cadastrarFuncionario(funcionario);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarFuncionario(@RequestBody @Valid FuncionarioRequestDTO funcionario,@PathVariable Integer id){
        return service.atualizarFuncionario(id, funcionario);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarFuncionario(@PathVariable Integer id){
        return service.deletarFuncionario(id);
    }
}
