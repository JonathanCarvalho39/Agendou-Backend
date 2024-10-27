package back.api.controller;


import back.domain.dto.request.UsuarioRequestDTO;
import back.domain.dto.response.UsuarioResponseDTO;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import back.service.service.UsuarioService;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
@Validated
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        System.out.println("Recebida requisição de login com email: " + email);
        return service.login(email, senha);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.status(200).body(service.listarUsuarios());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid UsuarioRequestDTO usuario) {
        return service.cadastrarUsuario(usuario);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@RequestBody @Valid UsuarioRequestDTO usuario,@RequestParam Integer id){
        return service.atualizarUsuario(id, usuario);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarUsuario(@RequestParam Integer id){
        return service.deletarUsuario(id);
    }
}