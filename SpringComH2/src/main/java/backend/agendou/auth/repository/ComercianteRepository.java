package backend.agendou.auth.repository;

import backend.agendou.auth.model.Comerciante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComercianteRepository extends JpaRepository<Comerciante, Integer> {
}