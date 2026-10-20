package br.senac.sp.tads.dsw.exemplo5.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.senac.sp.tads.dsw.exemplo5.dto.FuncionarioRequestDTO;
import br.senac.sp.tads.dsw.exemplo5.dto.FuncionarioResponseDTO;
import br.senac.sp.tads.dsw.exemplo5.model.Departamento;
import br.senac.sp.tads.dsw.exemplo5.model.Funcionario;
import br.senac.sp.tads.dsw.exemplo5.repository.DepartamentoRepository;
import br.senac.sp.tads.dsw.exemplo5.repository.FuncionarioRepository;

@Service 
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final DepartamentoRepository departamentoRepository;
    
    public FuncionarioService(FuncionarioRepository funcionarioRepository, DepartamentoRepository departamentoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public List<FuncionarioResponseDTO> listarTodos() {
        return funcionarioRepository.findAll()
                        .stream()
                        .map(f -> new FuncionarioResponseDTO(
                            f.getId(), f.getNome(), f.getDataContratacao(), f.getTrabalhoRemoto(), f.getDepartamento().getNome()))
                        .toList();
    }

    public FuncionarioResponseDTO buscarPorId(Long id) {
        Funcionario f = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));

        return new FuncionarioResponseDTO(f.getId(), f.getNome(), f.getDataContratacao(), f.getTrabalhoRemoto(), f.getDepartamento().getNome());
    }

    public FuncionarioResponseDTO criar(FuncionarioRequestDTO dto) {
        Departamento d = departamentoRepository.findById(dto.departamentoId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departamento não encontrado"));
        
        Funcionario f = new Funcionario();
        f.setNome(dto.nome());
        f.setDataContratacao(dto.dataContratacao());
        f.setTrabalhoRemoto(dto.trabalhoRemoto());
        f.setDepartamento(d);

        f = funcionarioRepository.save(f);

        return new FuncionarioResponseDTO(f.getId(), f.getNome(), f.getDataContratacao(), f.getTrabalhoRemoto(), f.getDepartamento().getNome());
    }

    public FuncionarioResponseDTO atualizar(Long id, FuncionarioRequestDTO dto) {
         Funcionario f = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));
        
        Departamento d = departamentoRepository.findById(dto.departamentoId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departamento não encontrado"));
        
        f.setNome(dto.nome());
        f.setDataContratacao(dto.dataContratacao());
        f.setTrabalhoRemoto(dto.trabalhoRemoto());
        f.setDepartamento(d);

        f = funcionarioRepository.save(f);

        return new FuncionarioResponseDTO(f.getId(), f.getNome(), f.getDataContratacao(), f.getTrabalhoRemoto(), f.getDepartamento().getNome());
    }

    public void apagar(Long id) {
        if(!funcionarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        funcionarioRepository.deleteById(id);
    }

    
}
