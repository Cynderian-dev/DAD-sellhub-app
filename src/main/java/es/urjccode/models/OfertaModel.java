package es.urjccode.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class OfertaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Float precio;
	private String titulo;
	private String categoria;
	private LocalDateTime fecha_creacion;
	private LocalDateTime fecha_cierre;
	@ManyToOne
	private UsuarioModel usuario_creador;
	@ManyToOne
	private UsuarioModel usuario_comprador;
	@OneToOne
	private ValoracionModel fk_valoracion;
	
	public OfertaModel() {
		
	}

	public OfertaModel(Float precio, String titulo, String categoria, LocalDateTime fecha_creacion,
			UsuarioModel usuario_creador) {
		this.precio = precio;
		this.titulo = titulo;
		this.categoria = categoria;
		this.fecha_creacion = fecha_creacion;
		this.usuario_creador = usuario_creador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public LocalDateTime getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDateTime fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public LocalDateTime getFecha_cierre() {
		return fecha_cierre;
	}

	public void setFecha_cierre(LocalDateTime fecha_cierre) {
		this.fecha_cierre = fecha_cierre;
	}

	public UsuarioModel getUsuario_creador() {
		return usuario_creador;
	}

	public void setUsuario_creador(UsuarioModel usuario_creador) {
		this.usuario_creador = usuario_creador;
	}

	public UsuarioModel getUsuario_comprador() {
		return usuario_comprador;
	}

	public void setUsuario_comprador(UsuarioModel usuario_comprador) {
		this.usuario_comprador = usuario_comprador;
	}

	public ValoracionModel getFk_valoracion() {
		return fk_valoracion;
	}

	public void setFk_valoracion(ValoracionModel fk_valoracion) {
		this.fk_valoracion = fk_valoracion;
	}
	
	
	
	
}
