package br.com.curso.forun.controller;



import java.net.URI;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.curso.forun.controller.dto.DetalhesDoTopicoDto;
import br.com.curso.forun.controller.dto.TopicoDto;
import br.com.curso.forun.controller.form.AtualizacaoTopicoForm;
import br.com.curso.forun.controller.form.TopicoForm;
import br.com.curso.forun.modelo.Topico;
import br.com.curso.forun.repository.CursoRepository;
import br.com.curso.forun.repository.TopicoReposiry;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoReposiry topicoReposiry;
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	@Cacheable(value = "listaDeTopicos")
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao){
	
		if(nomeCurso == null) {
		Page<Topico> topicos = topicoReposiry.findAll(paginacao);
		return TopicoDto.converterEmDto(topicos);
		}else {
			Page<Topico> topicos = topicoReposiry.findByCursoNome(nomeCurso, paginacao);
			return TopicoDto.converterEmDto(topicos);
		}
	}
	//tem que ser devolvido o status 201
	@PostMapping
	@Transactional  //serve para dizer ao spring para lipar o cache.
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.coverter(cursoRepository);
		topicoReposiry.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	//url dinamica
	@GetMapping("/{id}")       //para indicar que a variável vem ba url
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		Optional<Topico> optional = topicoReposiry.findById(id);
		if(optional.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(optional.get()));
		}
		
		
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
		Optional<Topico> optional = topicoReposiry.findById(id);
		if(optional.isPresent()) {
			Topico topico= form.atualizar(id, topicoReposiry);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Optional<Topico> optional = topicoReposiry.findById(id);
		if(optional.isPresent()) {
			topicoReposiry.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	
}












