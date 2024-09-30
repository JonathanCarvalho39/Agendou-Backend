package back.domain.dto.response;

import back.domain.model.Feriado;

import java.util.List;

public class FeriadoResponseDTO {
    private Resposta response;

    public Resposta getResponse(){
        return response;
    }

    public static class Resposta {
        private List<Feriado> holidays;

        public List<Feriado> getHolidays(){
            return holidays;
        }

        public void setFeriados(List<Feriado> feriados) {
            this.holidays = feriados;
        }
    }

    @Override
    public String toString() {
        return "FeriadoResponseDTO: " + response;
    }
}
