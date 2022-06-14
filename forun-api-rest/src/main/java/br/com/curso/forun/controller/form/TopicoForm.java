package br.com.curso.forun.controller.form;



import br.com.curso.forun.modelo.Curso;
import br.com.curso.forun.modelo.Topico;
import br.com.curso.forun.repository.CursoRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;



public class TopicoForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String titulo;
	@NotNull @NotEmpty @Length(min = 10)
    private String mensagem;
	@NotNull @NotEmpty
    private String nomeCurso;
    
    
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	public Topico coverter(CursoRepository rep) {
		Curso curso = rep.findByNome(nomeCurso);
		return new Topico(titulo, mensagem, curso);
	}
    
    
}
