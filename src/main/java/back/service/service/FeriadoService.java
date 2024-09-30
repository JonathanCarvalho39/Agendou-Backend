package back.service.service;




import back.api.strategy.OrdenacaoStrategy;
import back.domain.dto.response.FeriadoResponseDTO;
import back.domain.model.Feriado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FeriadoService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrdenacaoStrategy ordenacaoStrategy;

    @Value("${calendarific.api.key}")
    private String chaveApi;

    public ResponseEntity<List<Feriado>> buscarFeriadosOrdenados(String pais, int ano){
        try {
            String url = "https://calendarific.com/api/v2/holidays?api_key=" + chaveApi + "&country=" + pais + "&year=" + ano;

            FeriadoResponseDTO feriadoResponse = restTemplate.getForObject(url, FeriadoResponseDTO.class);

            if(feriadoResponse == null || feriadoResponse.getResponse() == null || feriadoResponse.getResponse().getHolidays().isEmpty()){
                return ResponseEntity.status(204).build(); // No Content, caso não haja feriados
            }

            List<Feriado> feriados = feriadoResponse.getResponse().getHolidays();
            List feriadosOrdenados = ordenacaoStrategy.ordenar(feriados);

            DateTimeFormatter formatadorSimples = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formatadorCompleto = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

            return ResponseEntity.status(200).body(feriados);

        } catch (Exception e){
            System.out.println("Erro ao buscar feriados: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
