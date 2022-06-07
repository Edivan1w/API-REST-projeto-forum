package br.com.curso.forun.config.validacao.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	//configuração de autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		                                             //trasnformar a senha em um hasch.
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	//configuração de autorizaçaõ
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//qual a url que se quer filtrar, e permiçao: se é para permitir ou bloquar
		.antMatchers(HttpMethod.GET ,"/topicos").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		//qualquer outra requisição tem que estar autenticada
		.anyRequest().authenticated()
		.and().csrf().disable()
		//está avisando para o spring que será stateless.
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	//configuração de recursos estáticos(js, css, imagens, etc..)
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}

public static void main(String[] args) {
	System.out.println(new BCryptPasswordEncoder().encode("123456"));
}
	
	
}
