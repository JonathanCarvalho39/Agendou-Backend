package back.api.strategy;



import back.domain.model.Feriado;

import java.util.List;

public interface OrdenacaoStrategy {

    List<Feriado> ordenar(List<Feriado> feriados);
}
