package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.endereco.Endereco;

public record DadosCadastroPaciente(
        @NotBlank
        String nome,

        @NotBlank
        @Min(0)
        @Max(100)
        String idade,

        @NotBlank
        @Email
        String email,

        @NotNull
        @Valid
        DadosEndereco endereco) {
}
