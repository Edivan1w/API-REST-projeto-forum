package br.com.curso.forun.controller;



import java.net.URI;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.curso.forun.controller.dto.TopicoDto;
import br.com.curso.forun.controller.form.TopicoForm;
import br.com.curso.forun.modelo.Topico;
import br.com.curso.forun.repository.CursoRepository;
import br.com.curso.forun.repository.TopicoReposiry;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoReposiry topicoReposiry;
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public List<TopicoDto> lista(String nomeCurso){
		if(nomeCurso == null) {
		List<Topico> topicos = topicoReposiry.findAll();
		return TopicoDto.converterEmDto(topicos);
		}else {
			List<Topico> topicos = topicoReposiry.findByCursoNome(nomeCurso);
			return TopicoDto.converterEmDto(topicos);
		}
	}
	//tem que ser devolvido o status 201
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.coverter(cursoRepository);
		topicoReposiry.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	//url dinamica
	@GetMapping("/{id}")       //para indicar que a variável vem ba url
	public TopicoDto detalhar(@PathVariable Long id) {
		Topico topico = topicoReposiry.findById(id).get();
		TopicoDto topicoDto = new TopicoDto(topico);
		
		return topicoDto;
	}

}












