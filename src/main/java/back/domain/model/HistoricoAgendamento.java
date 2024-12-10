package back.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "historico_agendamento")
public class HistoricoAgendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico_agendamento")
    private Integer id;

    @Column(name = "data")
    private LocalDateTime data;

    @Column(name = "status_anterior")
    private String statusAnterior;

    @Column(name = "status_atual")
    private String statusAtual;

    @Column(name = "nome_usuario")
    private String nomeUsuario;

    @Column(name = "nome_funcionario")
    private String nomeFuncionario;

    @Column(name = "nome_servico")
    private String nomeServico;
}
