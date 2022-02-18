package es.urjccode.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private int cantidad_valoraciones;
	private double puntuacion;
	@OneToMany
	private List<ListaModel> listas_usuario;
	
	public UsuarioModel() {
		
	}

	public UsuarioModel(String nombre, String contrasenya, List<ListaModel> listas_usuario) {
		this.nombre = nombre;
		this.contrasenya = contrasenya;
		this.cantidad_valoraciones = 0;
		this.puntuacion = 0.0;
		this.listas_usuario = new ArrayList<ListaModel>();
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

	public int getCantidad_valoraciones() {
		return cantidad_valoraciones;
	}

	public void setCantidad_valoraciones(int cantidad_valoraciones) {
		this.cantidad_valoraciones = cantidad_valoraciones;
	}

	public double getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}

	public List<ListaModel> getListas_usuario() {
		return listas_usuario;
	}

	public void setListas_usuario(List<ListaModel> listas_usuario) {
		this.listas_usuario = listas_usuario;
	}
	
	
	
	
	
}
