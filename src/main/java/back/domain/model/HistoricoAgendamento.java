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

    @ManyToOne
    @JoinColumn(name = "fk_agendamento")
    private Agendamento fkAgendamento;
}
