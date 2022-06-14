package br.com.curso.forun.config.validacao.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.curso.forun.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenSer {
	//anotação para indicar que a injeção será feita via application.porperties.
	@Value("${forum.jwt.expiration}")
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;

	//geração de token.
	public String gerarToken(Authentication authentication) {
		//agora tem-se que setar algumas coisas
		
		Usuario usuario = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		//pegar os milisegundos da variável hoje e somar.
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		//Para criar o token JWT, devemos utilizar a classe Jwts;
		return Jwts.builder()
				//quem está gerando o token
				.setIssuer("Sistema escola")
				//quem é o dono do token gerado: através do metodo getPrincipal recuperar o usuário e por sua buscar o id em String
				.setSubject(usuario.getId().toString())
				//dizer a data de geração
				.setIssuedAt(hoje)
				//dizer a data expiração
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				//e compactar
				.compact();
	}

	public boolean isTokenValido(String token) {
		//será jogado uma excepition por isso fazer o tratamento.
		
		try {
			Jwts
			//metodo faz o parse da string descriptografar
			.parser()
			//logo depois passar como parametro do metodo setSigningKey o secret que serve para criptogravar e descriptografar.
			.setSigningKey(this.secret)
			//Esse método devolve o Jws claims, que é um objeto onde se consegue recuperar o token e as informações que setou-se dentro do token
			.parseClaimsJws(token);
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
		
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		
		return Long.parseLong( claims.getSubject());
	}

	
}
