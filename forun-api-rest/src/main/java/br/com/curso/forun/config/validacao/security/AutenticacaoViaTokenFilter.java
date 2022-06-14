package br.com.curso.forun.config.validacao.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.curso.forun.modelo.Usuario;
import br.com.curso.forun.repository.UsuarioRepository;

//esta classa vai iterceptar antes de chegar na autenticação contoler e temos ques estender uma classe que irá fazer uma unica chamada.
//para o spring usar essa classe temos que anotar ela na classe SecurityConfiguration.

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{
	
	private TokenSer tokenService;
	private UsuarioRepository usuarioRepository;
	

	public AutenticacaoViaTokenFilter(TokenSer tokenServise, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenServise;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// nesse método será implementada a lógica para pegar o token.
		
		String token = recuperarToken(request);
		//criar um método que devolve um booelan para verificar se o token é valido.
		boolean valido = tokenService.isTokenValido(token);
		
		if (valido) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private void autenticarCliente(String token) {
		//metodo para falar para o spring autenticar o usuário.
		Long idUsauario = tokenService.getIdUsuario(token);
		Usuario usuario = usuarioRepository.findById(idUsauario).get();
		UsernamePasswordAuthenticationToken authetication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authetication);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		//atraves do request pegar o cabeçario
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		//vai pegar a String apartir do espaço.
		return token.substring(7, token.length());
	}

}
