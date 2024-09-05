package backend.agendou.auth.mapper;

import backend.agendou.auth.dto.request.ServicoRequestDTO;
import backend.agendou.auth.model.Servico;
import org.springframework.stereotype.Component;

@Component
public class ServicoMapper {
    public Servico toEntity(ServicoRequestDTO requestDTO) throws Exception {
        try {
            Servico servico = new Servico();
            servico.setNome(requestDTO.getNome());
            servico.setDescricao(requestDTO.getDescricao());
            servico.setPreco(requestDTO.getPreco());
            return servico;
        } catch (Exception e) {
            throw new Exception("Erro ao mapear ServicoRequestDTO para entidade" + e.getMessage());
        }
    }

    public ServicoRequestDTO toDTO(Servico servico) throws Exception {
        try {
            ServicoRequestDTO requestDTO = new ServicoRequestDTO();
            requestDTO.setNome(servico.getNome());
            requestDTO.setDescricao(servico.getDescricao());
            requestDTO.setPreco(servico.getPreco());
            return requestDTO;
        } catch (Exception e) {
            throw new Exception("Erro ao mapear entidade para ServicoRequestDTO" + e.getMessage());
        }
    }
}
