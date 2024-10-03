package back.api.controller;

import back.domain.dto.request.UsuarioRequestDTO;
import back.domain.dto.response.UsuarioResponseDTO;
import back.service.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "API de Usuários", tags = {"Usuário"})
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @ApiOperation(value = "Login de Usuário", notes = "Realiza o login de um usuário")
    @GetMapping("/login")
    public ResponseEntity<String> login(
            @ApiParam(value = "E-mail do usuário", required = true) @RequestParam String email,
            @ApiParam(value = "Senha do usuário", required = true) @RequestParam String senha) {
        System.out.println("Recebida requisição de login com email: " + email);
        try {
            return service.login(email, senha);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("E-mail ou senha inválido.");
        }
    }

    @ApiOperation(value = "Listar Usuários", notes = "Lista todos os usuários")
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        try {
            return ResponseEntity.status(200).body(service.listarUsuarios());
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @ApiOperation(value = "Cadastrar Usuário", notes = "Cadastra um novo usuário")
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarUsuario(
            @ApiParam(value = "Dados do usuário", required = true) @RequestBody @Valid UsuarioRequestDTO usuario) {
        try {
            service.cadastrarUsuario(usuario);
            return ResponseEntity.status(201).body("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Ocorreu um erro durante o cadastro.");
        }
    }

    @ApiOperation(value = "Atualizar Usuário", notes = "Atualiza um usuário existente")
    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarUsuario(
            @ApiParam(value = "Dados atualizados do usuário", required = true) @RequestBody @Valid UsuarioRequestDTO usuario) {
        try {
            service.atualizarUsuario(usuario);
            return ResponseEntity.status(202).body("Usuário atualizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Ocorreu um erro durante a atualização do usuário.");
        }
    }

    @ApiOperation(value = "Deletar Usuário", notes = "Deleta um usuário existente")
    @DeleteMapping("/deletar")
    public ResponseEntity<String> deletarUsuario(
            @ApiParam(value = "Dados do usuário", required = true) @RequestBody @Valid UsuarioRequestDTO usuario) {
        try {
            service.deletarUsuario(usuario);
            return ResponseEntity.status(202).body("Usuário deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Ocorreu um erro durante a deleção do usuário.");
        }
    }
}