package es.urjccode.models;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class ListaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombre;
	@ManyToOne
	private UsuarioModel fk_usuario;
	@OneToMany
	private List<OfertaModel> elementos;
	
	public ListaModel() {
		
	}

	public ListaModel(String nombre, UsuarioModel fk_usuario, List<OfertaModel> elementos) {
		super();
		this.nombre = nombre;
		this.fk_usuario = fk_usuario;
		this.elementos = elementos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public UsuarioModel getFk_usuario() {
		return fk_usuario;
	}

	public void setFk_usuario(UsuarioModel fk_usuario) {
		this.fk_usuario = fk_usuario;
	}

	public List<OfertaModel> getElementos() {
		return elementos;
	}

	public void setElementos(List<OfertaModel> elementos) {
		this.elementos = elementos;
	}
	
	

}