package br.senac.sp.tads.dsw.exemplo5.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.senac.sp.tads.dsw.exemplo5.dto.FuncionarioRequestDTO;
import br.senac.sp.tads.dsw.exemplo5.dto.FuncionarioResponseDTO;
import br.senac.sp.tads.dsw.exemplo5.service.FuncionarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<FuncionarioResponseDTO> listarTodos() {
        return service.listarTodos(); // Retorna 200 OK por padrão
    }
    
    @GetMapping("/{id}")
    public FuncionarioResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponseDTO criar(@RequestBody @Valid FuncionarioRequestDTO dto) {
        return service.criar(dto);
    }
    
    @PutMapping("/{id}")
    public FuncionarioResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid FuncionarioRequestDTO dto) {
    return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable Long id) {
        service.apagar(id);
    }
}
