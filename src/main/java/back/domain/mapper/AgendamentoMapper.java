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

        Funcionario funcionario = new Funcionario();
        funcionario.setId(agendamentoRequestDTO.getFkFuncionario().getId());
        agendamento.setFkFuncionario(funcionario);

        Usuario usuario = new Usuario();
        usuario.setId(agendamentoRequestDTO.getFkUsuario().getId());
        agendamento.setFkUsuario(usuario);

        List<Servico> servicos = agendamentoRequestDTO.getFkServicos();
        agendamento.setFkServicos(servicos);

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAId(agendamentoRequestDTO.getFkAvaliacao().getAId());
        agendamento.setFkAvaliacao(avaliacao);

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
        dto.setFkServicos(entity.getFkServicos());
        dto.setFkAvaliacao(entity.getFkAvaliacao());

        return dto;
    }
}
