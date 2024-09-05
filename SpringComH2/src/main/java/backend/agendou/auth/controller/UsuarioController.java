package backend.agendou.auth.controller;

import backend.agendou.auth.dto.request.UsuarioRequestDTO;
import backend.agendou.auth.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        System.out.println("Recebida requisição de login com email: " + email);
        try {
            return service.login(email, senha);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("E-mail ou senha inválido.");
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody @Valid UsuarioRequestDTO usuario) {
        try {
            service.cadastrarUsuario(usuario);
            return ResponseEntity.status(201).body("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Ocorreu um erro durante o cadastro.");
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarUsuario(@RequestBody @Valid UsuarioRequestDTO usuario){
        try {
            service.atualizarUsuario(usuario);
            return ResponseEntity.status(202).body("Usuário atualizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Ocorreu um erro durante a atualização do usuário.");
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<String> deletarUsuario(@RequestBody @Valid UsuarioRequestDTO usuario){
        try{
            service.deletarUsuario(usuario);
            return ResponseEntity.status(202).body("Usuário deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Ocorreu um erro durante a deleção do usuário.");
        }
    }
}
