package backend.agendou.auth.config;

import backend.agendou.auth.strategy.OrdenacaoStrategy;
import backend.agendou.auth.strategy.OrdenarPorDataStrategy;
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
