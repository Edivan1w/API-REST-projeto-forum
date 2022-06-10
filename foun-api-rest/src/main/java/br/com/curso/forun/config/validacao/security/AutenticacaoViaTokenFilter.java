package br.com.curso.forun.config.validacao.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

//esta classa vai iterceptar antes de chegar na autenticação contoler e temos ques estender uma classe que irá fazer uma unica chamada.
//para o spring usar essa classe temos que anotar ela na classe SecurityConfiguration.

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// nesse método será implementada a lógica para pegar o token.
		
		String token = recuperarToken(request);
		System.out.println(token);
		
		filterChain.doFilter(request, response);
		
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
