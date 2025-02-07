package med.voll.api.controler;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosDetalhamentoMedico;
import med.voll.api.dto.DadosListagemMedico;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
//########## (BUG) nem um dos endpoint estao retornando o endereco ##########
@RestController
@RequestMapping("/medicos")
public class MedicoControler {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional//gerencia as transacoes
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBilder){
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping //@PageableDefault(size = 10,page = 0, sort = {"nome"} = aqui esse comando diz se nao for passado
    //parametros na url caregue 10 registros da pagina 0 ordenada por nome
    public ResponseEntity <Page<DadosListagemMedico>> listar(@PageableDefault(size = 10,page = 0, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAll(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativa(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.escluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
// ### ESPLICACOES ###

//@RequestBody :  Essa anotação indica
// que os dados do corpo da requisição (JSON enviado pelo cliente)
// serão mapeados para o objeto DadosCadastroMedico.


//@PageableDefault(size = 10, page = 0, sort = {"nome"})
//size = 10: Define que, por padrão, a quantidade de registros retornados será 10 por página. Ou seja, se não for especificado outro valor para o número de registros, o servidor irá retornar 10 registros por vez.
//
//page = 0: Define que, por padrão, a página inicial será a página 0. Em sistemas de paginação, as páginas começam a partir do índice 0, então a página 0 representa a primeira página de resultados.
//
//sort = {"nome"}: Define que, por padrão, os resultados serão ordenados pelo campo nome em ordem crescente. Isso significa que, quando a requisição for feita, os dados serão listados em ordem alfabética pelo campo nome.
//
//3. Parâmetro Pageable:
//O parâmetro Pageable é uma interface do Spring Data que facilita a paginação. Ele armazena as informações sobre a página atual, o número de registros por página e os critérios de ordenação.

//size = 10: Limita o número de registros por página a 10.
//page = 0: Define a página inicial como a primeira (índice 0).
//sort = {"nome"}: Ordena os resultados pelo campo nome.