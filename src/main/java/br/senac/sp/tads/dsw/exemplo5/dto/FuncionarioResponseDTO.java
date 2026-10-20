package br.senac.sp.tads.dsw.exemplo5.dto;

import java.time.LocalDate;

public record FuncionarioResponseDTO (
    Long id,
    String nome,
    LocalDate dataContratacao,    //formato ano-mês-dia (sem horário)
    Boolean trabalhoRemoto,
    String nomeDepartamento
){
    
}
