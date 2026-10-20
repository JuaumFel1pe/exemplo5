package br.senac.sp.tads.dsw.exemplo5.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record FuncionarioRequestDTO(
    @NotBlank String nome,
    @PastOrPresent LocalDate dataContratacao,
    @NotNull Boolean trabalhoRemoto,
    @NotNull Long departamentoId
) {


}
