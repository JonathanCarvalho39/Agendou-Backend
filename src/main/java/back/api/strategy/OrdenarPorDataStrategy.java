package back.api.strategy;


import back.domain.model.Feriado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class OrdenarPorDataStrategy implements OrdenacaoStrategy {

    @Override
    public List<Feriado> ordenar(List<Feriado> feriados) {
        DateTimeFormatter formatadorSimples = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatadorCompleto = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

        feriados.sort(Comparator.comparing(feriado -> {
            String dataIso = feriado.getDate().getIso();
            try {
                if (dataIso.contains("T")) {
                    return LocalDate.parse(dataIso, formatadorCompleto);
                } else {
                    return LocalDate.parse(dataIso, formatadorSimples);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao parsear a data: " + dataIso, e);
            }
        }));

        return feriados;
    }
}
