package med.voll.api.dto;

import med.voll.api.domain.paciente.Paciente;

public record DadosListagemPaciente(

        String nome,
        String idade,
        String email) {

    public DadosListagemPaciente(Paciente paciente){
        this(paciente.getNome(), paciente.getIdade(), paciente.getEmail());
    }
}
