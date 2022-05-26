package br.com.curso.forun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.curso.forun.modelo.Topico;

public interface TopicoReposiry extends JpaRepository<Topico, Long> {

	List<Topico> findByCursoNome(String nomeCurso);

}
