package med.voll.api.paciente;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import med.voll.api.dto.DadosCadastroPaciente;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.endereco.Endereco;

@Entity(name = "paciente")
@Table(name = "pacientes")
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String idade;
    private String email;
    private Boolean ativo;

    @NotNull
    @Valid
    private Endereco endereco;

    public Paciente(){}

    public Paciente(@Valid DadosCadastroPaciente dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.idade = dados.idade();
        this.email = dados.email();
        this.endereco = new Endereco(dados.endereco());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        if (dados.nome() != null){
            this.nome = dados.nome();
        }
        if (dados.idade() != null){
            this.idade = dados.idade();
        }
        if (dados.email() != null){
            this.email = dados.email();
        }
        if (dados.endereco() != null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void deastivar(){
        this.ativo = false;
    }
}
