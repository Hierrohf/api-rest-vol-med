package med.voll.api.controler;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosCadastroPaciente;
import med.voll.api.dto.DadosListagemPaciente;
import med.voll.api.domain.paciente.DadosAtualizacaoPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
public class PacienteControler {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados){
        repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"})Pageable paginacao){
        return repository.findByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void desativar(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.deastivar();
    }

//    @DeleteMapping("/excluir/{id}")
//    @Transactional
//    public void excluir(@PathVariable Long id){
//        repository.deleteById(id);
//    }

}

// LEMBRETE DAS FUNCOES DOS METODOS HTTP

//GET
//
//Função: Solicitar dados do servidor.
//Exemplo: Buscar uma lista de produtos ou detalhes de um usuário.
//POST
//
//Função: Enviar dados para o servidor, geralmente para criar algo novo.
//Exemplo: Cadastrar um novo usuário.
//PUT
//
//Função: Atualizar um recurso existente, geralmente enviando todos os dados.
//Exemplo: Alterar os dados de um produto inteiro.
//PATCH
//
//Função: Atualizar parte de um recurso existente.
//Exemplo: Atualizar apenas o e-mail de um usuário.
//DELETE
//
//Função: Remover um recurso do servidor.
//Exemplo: Deletar um cliente da base de dados.