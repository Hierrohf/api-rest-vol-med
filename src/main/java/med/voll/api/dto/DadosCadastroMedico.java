package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.Especialidade;

public record DadosCadastroMedico(
       // @NotNull o @NotBlank subistitui o NotNull
       @NotBlank//verifica se esta vazio e null (so funciona para string) se nao for string se utiliza notnull
       String nome,

       @NotBlank
       @Email//valida se o atributo email esta no padrao d email
       String email,

       @NotBlank
       @Pattern(regexp = "\\d{11}")
       String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")//vrm tem q ser padrao de 4 a 6 digitos e essa anotacao garante que o campo tenha de 4 ate 6 digitos
        String crm,

        @NotNull//diz que o campo nao pode ser null
        Especialidade especialidade,

        @NotNull
        @Valid// diz para o beenValidation que essa class DTO aqui esta sendo validada, porem dentro de um dos atributos
        // e um outro DTO e nela tbm estara sendo validada
        DadosEndereco endereco) {

}
