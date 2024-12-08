package back.service.service;

import back.api.config.security.TokenService;
import back.domain.dto.request.UsuarioRequestDTO;
import back.domain.dto.response.LoginUserResponseDTO;
import back.domain.dto.response.UsuarioResponseDTO;
import back.domain.enums.UsuarioRole;
import back.domain.mapper.UsuarioMapper;
import back.domain.model.Usuario;
import back.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioMapper mapper;

    @InjectMocks
    private UsuarioService usuarioService;


    @Test
    @DisplayName("Deve retornar o usuário e o token quando a autenticação for bem-sucedida")
    void deveRetornarOkQuandoCredenciaisCorretas() {

        String email = "test@example.com";
        String senha = "password";
        String senhaCriptografada = passwordEncoder.encode(senha);
        String token = "jwt-token";
        Usuario usuario = new Usuario(
                1,
                "Test User",
                email,
                senhaCriptografada,
                "11999999999",
                UsuarioRole.USER
        );
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(
                1, // ID
                "Test User",
                email,
                null,
                "11999999999",
                UsuarioRole.USER,
                token // Token
        );

        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
        Mockito.when(passwordEncoder.matches(senha, usuario.getPassword())).thenReturn(true);
        Mockito.when(tokenService.generateTokenUser(usuario)).thenReturn(token);
        Mockito.when(mapper.toUsuarioResponseDto(usuario)).thenReturn(responseDTO);

        ResponseEntity<?> response = usuarioService.login(email, senha);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        LoginUserResponseDTO loginResponse = (LoginUserResponseDTO) response.getBody();
        assertEquals(token, loginResponse.getToken());
        assertEquals(responseDTO, loginResponse.getUsuario());
    }


    @Test
    @DisplayName("Deve retornar erro quando o email não estiver cadastrado")
    void testaSeOEmailExiste() {

        String email = "nonexistent@example.com";
        String senha = "password";

        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<?> response = usuarioService.login(email, senha);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    @DisplayName("Deve retornar erro quando a senha estiver incorreta")
    void testaSeASenhaEstaCorreta() {

        String email = "test@example.com";
        String senha = "wrong-password";
        Usuario usuario = new Usuario(
                1,
                "Test User",
                email,
                senha,
                "11999999999",
                UsuarioRole.USER
        );

        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
        Mockito.when(passwordEncoder.matches(senha, usuario.getPassword())).thenReturn(false);

        ResponseEntity<?> response = usuarioService.login(email, senha);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    @DisplayName("Cenário em que o usuário for cadastrado com sucesso")
    void cadastroDeUsuarioComSucesso() {

        UsuarioRequestDTO dto = new UsuarioRequestDTO("Test User", "test@example.com", "password", "11999999999", UsuarioRole.USER);
        Usuario usuario = new Usuario(1, "Test User", "test@example.com", "encryptedPassword", "11999999999", UsuarioRole.USER);

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(1, "Test User", "test@example.com", null, "11999999999", UsuarioRole.USER, "token");

        Mockito.when(repository.existsByEmail(dto.getEmail())).thenReturn(false);
        Mockito.when(mapper.toEntity(dto)).thenReturn(usuario);
        Mockito.when(passwordEncoder.encode(dto.getSenha())).thenReturn("encryptedPassword");
        Mockito.when(repository.save(usuario)).thenReturn(usuario);
        Mockito.when(mapper.toUsuarioResponseDto(usuario)).thenReturn(responseDTO);

        ResponseEntity<?> response = usuarioService.cadastrarUsuario(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(responseDTO, response.getBody());
    }


    @Test
    @DisplayName("Teste para ver se o email já não está cadastrado")
    void cadastrarUsuarioComUmEmailJaExistente() {

        UsuarioRequestDTO dto = new UsuarioRequestDTO("Test User", "test@example.com", "password", "11999999999", UsuarioRole.USER);

        Mockito.when(repository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> usuarioService.cadastrarUsuario(dto));
    }


    @Test
    @DisplayName("Deve retornar erro quando o mapeamento do usuário falhar")
    void cadastroDeUsuarioComErroNaHoraDePassarParaOMapper() {

        UsuarioRequestDTO dto = new UsuarioRequestDTO("Test User", "test@example.com", "password", "11999999999", UsuarioRole.USER);

        Mockito.when(repository.existsByEmail(dto.getEmail())).thenReturn(false);

        Usuario usuario = new Usuario();
        Mockito.when(mapper.toEntity(dto)).thenReturn(usuario);

        ResponseEntity<?> response = usuarioService.cadastrarUsuario(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    @DisplayName("Deve retornar ok e o usuário atualizado quando ele for encontrado e atualizado com sucesso")
    void encontraUsuarioEAtualizaComSucesso() {

        Integer id = 1;
        UsuarioRequestDTO usuarioRequest = new UsuarioRequestDTO("Updated Name", "updated@example.com", "newpassword", "11988888888", UsuarioRole.USER);

        Usuario usuarioExistente = new Usuario(id, "Old Name", "old@example.com", "oldpassword", "11999999999", UsuarioRole.USER);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(usuarioExistente));

        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuarioExistente);

        Mockito.when(mapper.toUsuarioResponseDto(Mockito.any(Usuario.class))).thenReturn(new UsuarioResponseDTO(id, "Updated Name", "updated@example.com", "newpassword",
                "11988888888", UsuarioRole.USER, "token"));

        ResponseEntity<?> response = usuarioService.atualizarUsuario(id, usuarioRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof UsuarioResponseDTO);
        UsuarioResponseDTO responseDTO = (UsuarioResponseDTO) response.getBody();
        assertEquals("Updated Name", responseDTO.getNome());
    }


    @Test
    @DisplayName("Deve retornar erro quando o usuário não for encontrado")
    void procuraUsuarioParaSerAtualizadoENaoEncontra() {

        Integer id = 1;
        UsuarioRequestDTO usuarioRequest = new UsuarioRequestDTO("Updated Name", "updated@example.com", "newpassword", "11988888888", UsuarioRole.USER);

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = usuarioService.atualizarUsuario(id, usuarioRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não encontrado.", response.getBody());
    }


    @Test
    @DisplayName("Deve retornar ok quando o usuário for deletado com sucesso")
    void deletarUsuarioComSucesso() {

        Integer id = 1;
        Usuario usuarioExistente = new Usuario(id, "Test User", "test@example.com", "password", "11999999999", UsuarioRole.USER);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        Mockito.doNothing().when(repository).delete(Mockito.any(Usuario.class));

        ResponseEntity<?> response = usuarioService.deletarUsuario(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioExistente, response.getBody());
    }


    @Test
    @DisplayName("Deve retornare rro quando o usuário não for encontrado")
    void procurarUsuarioParaDeletarENaoEncontrar() {

        Integer id = 1;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = usuarioService.deletarUsuario(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não encontrado.", response.getBody());
    }
}