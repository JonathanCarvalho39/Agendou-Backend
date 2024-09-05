package backend.agendou.auth.service;

import backend.agendou.auth.dto.request.ServicoRequestDTO;
import backend.agendou.auth.model.Servico;
import backend.agendou.auth.repository.ServicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository repository;

    public List<Servico> listarServicos() {
        return repository.findAll();
    }

    public void atualizarServico(@Valid ServicoRequestDTO servico) {
        Optional<Servico> optionalServico = repository.findById(servico.getId());

        if (optionalServico.isPresent()) {
            Servico servicoAtualizado = optionalServico.get();
            servicoAtualizado.setNome(servico.getNome());
            servicoAtualizado.setPreco(servico.getPreco());
            servicoAtualizado.setDescricao(servico.getDescricao());
            servicoAtualizado.setDuracaoServico(servico.getDuracaoServico());

            repository.save(servicoAtualizado);
        } else {
            throw new RuntimeException("Serviço não encontrado.");
        }
    }

    public void deletarServico(@Valid ServicoRequestDTO servico) {
        repository.deleteById(servico.getId());
    }

    public void cadastrarServico(@Valid ServicoRequestDTO servico) {
        Servico novoServico = new Servico();
        novoServico.setId(null);
        novoServico.setNome(servico.getNome());
        novoServico.setPreco(servico.getPreco());
        novoServico.setDescricao(servico.getDescricao());
        novoServico.setDuracaoServico(servico.getDuracaoServico());
        repository.save(novoServico);
    }
}
