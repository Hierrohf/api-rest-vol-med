package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //define que essa class e uma class de confuguracao
@EnableWebSecurity //diz ao spring que vamos confugurar as config de segurancao (spring security)
public class SecurityConfiguration {

    private SecurityFilter securityFilter;

    //explicacao na linha 41 (ATUALIZAR A EXPLICACAO)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityFilter securityFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Desabilita CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define sessão como stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll() // Permite acesso ao login
                        .anyRequest().authenticated() // Exige autenticação para qualquer outra requisição
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro antes da autenticação padrão

        return http.build(); //retorna as cofig
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
//#####NAO E UMA BOA PRATICA DEIXAR ESSAS ANOTACOES QUI O IDEAL E DOCUMENTAR O PROJETO#####
//@Bean → Essa anotação indica que o metodo retorna um bean gerenciado pelo Spring, ou seja, um componente
// configurado e disponível no contexto da aplicação.
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception →
//Define um metodo que retorna um SecurityFilterChain, que é um conjunto de filtros de segurança usados pelo Spring Security para proteger a aplicação.
//HttpSecurity http → O Spring injeta uma instância de HttpSecurity, que permite configurar a segurança da aplicação.

//http.csrf(csrf -> csrf.disable()) → Aqui estamos desabilitando a proteção CSRF.
//csrf -> csrf.disable() → Isso usa a API funcional do Java para desativar o CSRF.
//Por que desabilitar?
//Em aplicações stateless (sem sessão), como APIs REST, o CSRF não é necessário, pois o ataque CSRF depende de sessões armazenadas no servidor.
//Em sistemas que usam JWT (JSON Web Token), a autenticação é feita por tokens e não por sessão, então a proteção CSRF geralmente é removida.

//sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//Configura a política de gerenciamento de sessão.
//SessionCreationPolicy.STATELESS → Indica que a aplicação não deve criar sessões.
//Isso significa que cada requisição precisa conter os dados de autenticação (como um token JWT).
//Nenhuma sessão será salva no servidor.

//return http.build();
//Finaliza a configuração da segurança e retorna um SecurityFilterChain, que o Spring Security usa para proteger a aplicação.

//CSRF (Cross-Site Request Forgery), ou Falsificação de Requisição entre Sites, é um tipo de ataque que
// força um usuário autenticado a executar ações indesejadas em um site no qual está logado, sem o
// seu consentimento. como estamos utilizando tokens nao e necesario o CSRF estar ativo