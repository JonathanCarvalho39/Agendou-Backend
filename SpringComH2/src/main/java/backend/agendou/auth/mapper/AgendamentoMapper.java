package backend.agendou.auth.mapper;

import backend.agendou.auth.dto.request.AgendamentoRequestDTO;
import backend.agendou.auth.dto.response.AgendamentoResponseDTO;
import backend.agendou.auth.model.Agendamento;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoMapper {

    public Agendamento toEntity(AgendamentoRequestDTO agendamentoRequestDTO) {
        Agendamento agendamento = new Agendamento();

        agendamento.setDataHoraCorte(agendamentoRequestDTO.getDataHoraCorte());
        agendamento.setProfissional(agendamentoRequestDTO.getProfissional());
        return agendamento;
    }

    public AgendamentoResponseDTO toDTO(Agendamento entity) {
        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();

        dto.setId(entity.getId());
        dto.setDataHoraCorte(entity.getDataHoraCorte());
        dto.setProfissional(entity.getProfissional());
        return dto;
    }
}
