package es.urjccode.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
// Se añade una constraint para garantizar que no existen listas con el mismo nombre para el mismo usuario
@Table(name="ListaModel", uniqueConstraints= @UniqueConstraint(name="UniqueFkUsuarioAndNombre", columnNames= {"fk_usuario_id", "nombre"}))
public class ListaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombre;
	@ManyToOne
	private UsuarioModel fkUsuario;
	@ManyToMany
	private List<OfertaModel> elementos;
	
	public ListaModel() {
		
	}

	public ListaModel(String nombre, UsuarioModel fk_usuario, List<OfertaModel> elementos) {
		super();
		this.nombre = nombre;
		this.fkUsuario = fk_usuario;
		this.elementos = elementos;
	}
	
	public ListaModel(String nombre, UsuarioModel fk_usuario) {
		super();
		this.nombre = nombre;
		this.fkUsuario = fk_usuario;
		this.elementos = new LinkedList<OfertaModel>();
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

	public UsuarioModel getFkUsuario() {
		return fkUsuario;
	}

	public void setFkUsuario(UsuarioModel fk_usuario) {
		this.fkUsuario = fk_usuario;
	}

	public List<OfertaModel> getElementos() {
		return elementos;
	}

	public void setElementos(List<OfertaModel> elementos) {
		this.elementos = elementos;
	}
	
	public void addElemento(OfertaModel e) {
		this.elementos.add(e);
	}

	public boolean buscarOferta(long oferta) {
		for (OfertaModel o:elementos) {
			if (o.getId() == oferta) {
				return true;
			}
		}
		return false;
	}

}
