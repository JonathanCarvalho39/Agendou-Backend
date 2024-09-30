package back.api.config;


import back.api.strategy.OrdenacaoStrategy;
import back.api.strategy.OrdenarPorDataStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CalendarApiConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public OrdenacaoStrategy ordenacaoStrategy(){
        return new OrdenarPorDataStrategy();
    }
}
