package br.com.curso.forun.config.validacao.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.curso.forun.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
@Profile("prod")
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	@Autowired
	private TokenSer tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
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

	//configuração de autorização.
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//qual a url que se quer filtrar, e permiçao: se é para permitir ou bloquar
		.antMatchers(HttpMethod.GET ,"/topicos").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
		.antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
		.antMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
		.antMatchers(HttpMethod.DELETE, "/topicos/**").hasRole("MODERADOR")
		//qualquer outra requisição tem que estar autenticada
		.anyRequest().authenticated()
		.and().csrf().disable()
		//está avisando para o spring que será stateless.
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		//para o spring usar a clase de filtro antes da dele.
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
	}

	//configuração de recursos estáticos(js, css, imagens, etc..)
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//	    web.ignoring()
//	       .antMatchers("/**.html",
//	                               "/v3/api-docs/**",
//	                               "/webjars/**",
//	                               "/configuration/**",
//	                               "/swagger-resources/**",
//	                               "/swagger-ui/**");
//	}

public static void main(String[] args) {
	System.out.println(new BCryptPasswordEncoder().encode("123456"));
}
	
	
}
