package backend.agendou.auth.repository;

import backend.agendou.auth.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    List<Agendamento> findByDataHoraCorteBetween(LocalDateTime inicio, LocalDateTime fim);

    Optional<Agendamento> findByDataHoraCorte(LocalDateTime dataHoraCorte);
}
