package br.com.curso.forun.repository;


import org.junit.jupiter.api.Test;
import org.junit.Assert;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.curso.forun.modelo.Curso;



@RunWith(SpringRunner.class)
@DataJpaTest
public class CursoRepositoryTest {
	
	@Autowired
	private CursoRepository cursoRepository;

	@Test
	public void deveriaCarregarUmCursoAobuscarPeloNome() {
		String nomeCurso = "Java";
		Curso nome = cursoRepository.findByNome(nomeCurso);
		Assert.assertNotNull(nome);
		Assert.assertEquals(nomeCurso, nome.getNome());;
	}

	@Test
	public void naoDeveriaCarregarUmCursoCujoNomeNaoEstaCadastrado() {
		String nomeCurso = "html";
		Curso nome = cursoRepository.findByNome(nomeCurso);
		Assert.assertNull(nome);
	}
	
}
