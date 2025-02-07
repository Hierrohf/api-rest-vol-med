package med.voll.api.controler;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.dto.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoControle {

    @Autowired // Agora a injeção do AuthenticationManager funcionará corretamente
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var autenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(autenticationToken);
        var tokenJwt = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJwt));
    }
}

//public ResponseEntity<?> efetuarLogin(...)
//
//Define um metodo público que retorna um ResponseEntity, que é um objeto usado pelo Spring para construir respostas HTTP.
//O <?> indica que o tipo da resposta é genérico (pode ser qualquer tipo, mas nesse caso, não estamos retornando um corpo na resposta).
//@RequestBody @Valid DadosAutenticacao dados
//
//@RequestBody → Indica que os dados do corpo da requisição (JSON enviado pelo cliente) serão convertidos para um objeto DadosAutenticacao.
//@Valid → Faz com que o Spring valide automaticamente os campos do objeto DadosAutenticacao, de acordo com as anotações de validação (@NotNull, @Size, etc.).
//DadosAutenticacao → Deve ser uma classe que contém os atributos login e senha, representando os dados de autenticação.

//Criação do token de autenticação:
//UsernamePasswordAuthenticationToken é uma implementação do Spring Security usada para representar credenciais de login (usuário e senha).
//dados.login() e dados.senha() → Extraem o login e a senha enviados pelo usuário no JSON da requisição.
//O token será usado para autenticar o usuário no Spring Security.
//java
//Copiar
//Editar
//var authentication = manager.authenticate(token);
//Autenticação do usuário:
//manager.authenticate(token) → O AuthenticationManager do Spring recebe o token e tenta autenticar o usuário.
//O manager verifica as credenciais no UserDetailsService e, se estiverem corretas, autentica o usuário.
//Se a autenticação falhar (usuário não encontrado ou senha errada), uma exceção será lançada.

//return ResponseEntity.ok().build();
//Retorna uma resposta HTTP 200 (OK):
//Como o metodo não está retornando um token JWT ou outra informação no corpo da resposta, ele apenas retorna um ResponseEntity.ok() sem conteúdo.
//Caso queira retornar um token JWT, por exemplo, esse retorno poderia ser algo como:
//java
//Copiar
//Editar
//return ResponseEntity.ok(new TokenDTO(tokenGerado));