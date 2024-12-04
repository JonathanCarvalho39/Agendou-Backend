package back.domain.mapper;


import back.domain.dto.request.AgendamentoRequestDTO;
import back.domain.dto.response.AgendamentoResponseDTO;
import back.domain.dto.response.UsuarioResponseDTO;
import back.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgendamentoMapper {

    public Agendamento toEntity(AgendamentoRequestDTO agendamentoRequestDTO) {
        Agendamento agendamento = new Agendamento();

        agendamento.setId(agendamentoRequestDTO.getId());
        agendamento.setData(agendamentoRequestDTO.getData());

        if (agendamentoRequestDTO.getFkFuncionario() != null) {
            Funcionario funcionario = new Funcionario();
            funcionario.setId(agendamentoRequestDTO.getFkFuncionario());
            agendamento.setFkFuncionario(funcionario);
        }

        if (agendamentoRequestDTO.getFkUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(agendamentoRequestDTO.getFkUsuario());
            agendamento.setFkUsuario(usuario);
        }

        if (agendamentoRequestDTO.getFkServico() != null) {
            Servico servico = new Servico();
            servico.setId(agendamentoRequestDTO.getFkServico());
            agendamento.setFkServico(servico);
        }

        return agendamento;
    }


    public AgendamentoResponseDTO toAgendamentoResponseDto(Agendamento entity) {

        if (entity == null) {
            return null;
        }

        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();

        dto.setId(entity.getId());
        dto.setData(entity.getData());
        dto.setFkFuncionario(entity.getFkFuncionario());
        dto.setFkUsuario(entity.getFkUsuario());
        dto.setFkServico(entity.getFkServico());
        dto.setFkAvaliacao(entity.getFkAvaliacao());

        return dto;
    }
}
