package br.com.curso.forun.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.curso.forun.config.validacao.security.TokenService;
import br.com.curso.forun.controller.form.FomLogin;

@RestController
@RequestMapping("/auth")
public class AutenticaçãoController {
	
	@Autowired  //esta classe não faz ijeção de dependencia automaticamente, por isso tem que usar a 
	//classe securityConfiguration. Lá existe um metodo que sabe ijetar
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;

	
	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid FomLogin login){
		//agora tem que chamar o método authenticate que irá receber o objeto que irá conter o login e senha.
		UsernamePasswordAuthenticationToken dadosUsuario = login.converter();
		//é preciso fazer um tratamento de erro para que se devolva 400
		
		try {
			Authentication authentication = authenticationManager.authenticate(dadosUsuario);
			//antes de devolver o "OK" temos que gerar o token
			String token = tokenService.gerarToken(authentication);
			System.out.println(token);
			return ResponseEntity.ok().build();
			
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
}
