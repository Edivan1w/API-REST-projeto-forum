package br.com.curso.forun.config.validacao.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.curso.forun.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	//anotação para indicar que a injeção será feita via application.porperties.
	@Value("${forum.jwt.expiration}")
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		//agora tem-se que setar algumas coisas
		
		Usuario usuario = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		//pegar os milisegundos da variável hoje e somar.
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
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

	
}
