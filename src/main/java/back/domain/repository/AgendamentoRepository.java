package back.domain.repository;


import back.domain.dto.response.AgendamentoPorMesDTO;
import back.domain.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    Optional<Agendamento> findByData(LocalDateTime data);
    List<Agendamento> findAllByFkFuncionarioId(Integer idFuncionario);
    List<Agendamento> findAll();
    Optional<Agendamento> findById(Integer id);

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.fkUsuario.id = :fkUsuario AND a.data BETWEEN :dataInicio AND :dataFim")
    Long countAgendamentosPorUsuarioNoPeriodo(@Param("fkUsuario") Integer fkUsuario,
                                              @Param("dataInicio") LocalDateTime dataInicio,
                                              @Param("dataFim") LocalDateTime dataFim);


    List<Agendamento> findByDataBetween(LocalDateTime datInicio, LocalDateTime dataFim);

    @Query("SELECT FUNCTION('MONTH', a.data) as mes, COUNT(a.id) as total " +
            "FROM Agendamento a " +
            "GROUP BY FUNCTION('MONTH', a.data) " +
            "ORDER BY FUNCTION('MONTH', a.data)")
    List<Object[]> findAgendamentosPorMes();




}
