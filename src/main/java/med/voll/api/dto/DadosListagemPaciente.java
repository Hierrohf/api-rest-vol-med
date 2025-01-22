package med.voll.api.dto;

import med.voll.api.endereco.DadosEndereco;
import med.voll.api.paciente.Paciente;

public record DadosListagemPaciente(

        String nome,
        String idade,
        String email) {

    public DadosListagemPaciente(Paciente paciente){
        this(paciente.getNome(), paciente.getIdade(), paciente.getEmail());
    }
}
