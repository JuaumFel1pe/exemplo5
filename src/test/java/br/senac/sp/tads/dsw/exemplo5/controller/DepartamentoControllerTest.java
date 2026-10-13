package br.senac.sp.tads.dsw.exemplo5.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;

import br.senac.sp.tads.dsw.exemplo5.dto.DepartamentoRequestDTO;
import br.senac.sp.tads.dsw.exemplo5.model.Departamento;
import br.senac.sp.tads.dsw.exemplo5.repository.DepartamentoRepository;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional 
public class DepartamentoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartamentoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarDeparmentoComSucesso() throws Exception {
        // 1 - Criar o objeto que queremos enviar
        DepartamentoRequestDTO dto = new DepartamentoRequestDTO("Turma B - TADS", 3600.00);

        // 2 - Converter o objeto JAVA para JSON (string)
        String json = objectMapper.writeValueAsString(dto);

        // 3 - Enviar o POST para a API
        mockMvc.perform(
                        post("/api/departamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        )
        // 4 - Checar se a resposta é o que foi enviado
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id").exists()) //verifica se o ID existe
                .andExpect(jsonPath("$.nome").value("Turma B - TADS")) //verifica se o nome existe
                .andExpect(jsonPath("$.orcamento").value(3600.00)); //verifica se o orçamento existe
    }

    @Test 
    void deveListarTodosOsDepartamentos() throws Exception{
        DepartamentoRequestDTO dto = new DepartamentoRequestDTO("Turma B - TADS", 3600.00);

        Departamento departamento = new Departamento();
        departamento.setNome(dto.nome());
        departamento.setOrcamento(dto.orcamento());
        
        repository.save(departamento);

        mockMvc.perform(get("/api/departamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test 
    void deveBuscarDepartamentoPorId() throws Exception{
         DepartamentoRequestDTO dto = new DepartamentoRequestDTO("Turma B - TADS", 3600.00);

        Departamento departamento = new Departamento();
        departamento.setNome(dto.nome());
        departamento.setOrcamento(dto.orcamento());

        repository.save(departamento);

        mockMvc.perform(get("/api/departamentos/" + departamento.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departamento.getId())) //verifica se o ID existe
                .andExpect(jsonPath("$.nome").value("Turma B - TADS")) //verifica se o nome existe
                .andExpect(jsonPath("$.orcamento").value(3600.00)); //verifica se o orçamento existe
    }

    @Test 
    void deveAtualizarDepartamento() throws Exception{
        DepartamentoRequestDTO dto = new DepartamentoRequestDTO("Turma B - TADS", 3600.00);

        Departamento departamento = new Departamento();
        departamento.setNome(dto.nome());
        departamento.setOrcamento(dto.orcamento());

        Departamento departamentoSalvo = repository.save(departamento);

        //Cria os novos dados para PUT
        departamentoSalvo.setNome("Turma do Luis");
        String json = objectMapper.writeValueAsString(departamentoSalvo);

        //Executa e verifica se atualizou
        mockMvc.perform(
                        put("/api/departamentos/" + departamentoSalvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        )
                .andExpect(status().isOk()) //Ok
                .andExpect(jsonPath("$.nome").value("Turma do Luis")) //verifica se o nome existe
                .andExpect(jsonPath("$.orcamento").value(3600.00)); //verifica se o orçamento existe
    }

    @Test 
    void deveApagarDepartamento() throws Exception{
        DepartamentoRequestDTO dto = new DepartamentoRequestDTO("Turma B - TADS", 3600.00);

        Departamento departamento = new Departamento();
        departamento.setNome(dto.nome());
        departamento.setOrcamento(dto.orcamento());

        departamento = repository.save(departamento);

        //Deleta e espera 204 No Content
        mockMvc.perform(delete("/api/departamentos/" + departamento.getId()))
                .andExpect(status().isNoContent());

        //Confirma que apagou tentando buscar(espera 404 Not Found)
        mockMvc.perform(get("/api/departamentos/" + departamento.getId()))
                .andExpect(status().isNotFound());
    }
}
