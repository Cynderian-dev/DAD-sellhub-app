package es.urjccode.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UsuarioModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique=true)
	private String nombre;
	private String contrasenya;
	private int cantidadValoraciones;
	private double puntuacion;
	@OneToMany
	private List<ListaModel> listasUsuario;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;
	
	public UsuarioModel() {
		
	}

	public UsuarioModel(String nombre, String contrasenya) {
		this.nombre = nombre;
		this.contrasenya = contrasenya;
		this.cantidadValoraciones = 0;
		this.puntuacion = 0.0;
		this.listasUsuario = new ArrayList<ListaModel>();
		this.roles = List.of("USER");
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

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public int getCantidadValoraciones() {
		return cantidadValoraciones;
	}

	public void setCantidadValoraciones(int cantidad_valoraciones) {
		this.cantidadValoraciones = cantidad_valoraciones;
	}

	public double getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}

	public List<ListaModel> getListasUsuario() {
		return listasUsuario;
	}

	public void setListasUsuario(List<ListaModel> listas_usuario) {
		this.listasUsuario = listas_usuario;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
		
}
