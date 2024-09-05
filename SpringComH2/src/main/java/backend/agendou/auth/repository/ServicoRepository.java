package backend.agendou.auth.repository;

import backend.agendou.auth.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

}
