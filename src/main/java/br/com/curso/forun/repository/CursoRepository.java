package br.com.curso.forun.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.curso.forun.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nomeCurso);

}
