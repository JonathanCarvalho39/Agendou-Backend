package org.example.springcomh2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;
    Usuario contaLua = new Usuario("lua", "lua.leite", "lua123", 1);

    @PostMapping("/cadastrar")
    public Usuario cadastrar(@RequestBody Usuario usuarioCadastrar) {
        Usuario usuarioSalvo = this.repository.save(usuarioCadastrar);
        return usuarioSalvo;
    }

    @GetMapping("/{id}")
    public List<Usuario> listar() {
        return this.repository.findAll();
    }

    @PutMapping("/{id}/senha")
    public Usuario alterarSenha(@PathVariable Integer id, @RequestBody String novaSenha) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setSenha(novaSenha);
        return repository.save(usuario);
    }
}
