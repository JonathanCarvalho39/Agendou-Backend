package backend.agendou.auth.strategy;


import backend.agendou.auth.model.Feriado;

import java.util.List;

public interface OrdenacaoStrategy {

    List<Feriado> ordenar(List<Feriado> feriados);
}
