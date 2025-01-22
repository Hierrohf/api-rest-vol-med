package med.voll.api.dto;

import med.voll.api.medico.Especialidade;
import med.voll.api.medico.Medico;

public record DadosListagemMedico(

        Long id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade) {

//esse construtor e necesario para atribuir valores aos atributos
    public DadosListagemMedico (Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
