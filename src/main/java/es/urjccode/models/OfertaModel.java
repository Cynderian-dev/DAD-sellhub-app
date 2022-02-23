package es.urjccode.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import es.urjccode.EnumCategorias;

@Entity
public class OfertaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private double precio;
	private String titulo;
	@Enumerated(EnumType.STRING)
	private EnumCategorias categoria;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaCierre;
	@ManyToOne
	private UsuarioModel usuarioCreador;
	@ManyToOne
	private UsuarioModel usuarioComprador;
	@OneToOne(mappedBy="fkOferta")
	private ValoracionModel fkValoracion;
	@ManyToMany(mappedBy="elementos")
	private List<ListaModel> listas;
	
	public OfertaModel() {
		
	}

	public OfertaModel(double precio, String titulo, EnumCategorias categoria, LocalDateTime fechaCreacion,
			UsuarioModel usuarioCreador) {
		this.precio = precio;
		this.titulo = titulo;
		this.categoria = categoria;
		this.fechaCreacion = fechaCreacion;
		this.usuarioCreador = usuarioCreador;
		List<ListaModel> listas = new ArrayList<ListaModel>();
		this.listas = listas;
	}
	
	public void actualizar(double precio, String titulo, EnumCategorias categoria) {
		this.precio = precio;
		this.titulo = titulo;
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public EnumCategorias getCategoria() {
		return categoria;
	}

	public void setCategoria(EnumCategorias categoria) {
		this.categoria = categoria;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fecha_creacion) {
		this.fechaCreacion = fecha_creacion;
	}

	public LocalDateTime getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDateTime fecha_cierre) {
		this.fechaCierre = fecha_cierre;
	}

	public UsuarioModel getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(UsuarioModel usuario_creador) {
		this.usuarioCreador = usuario_creador;
	}

	public UsuarioModel getUsuarioComprador() {
		return usuarioComprador;
	}

	public void setUsuarioComprador(UsuarioModel usuario_comprador) {
		this.usuarioComprador = usuario_comprador;
	}

	public ValoracionModel getFkValoracion() {
		return fkValoracion;
	}

	public void setFkValoracion(ValoracionModel fk_valoracion) {
		this.fkValoracion = fk_valoracion;
	}
	
	public String getNombreUsu() {
		return usuarioCreador.getNombre();
	}
	
	public List<ListaModel> getListas() {
		return listas;
	}

	public void setListas(List<ListaModel> listas) {
		this.listas = listas;
	}
	
	public void addElemento(ListaModel e) {
		this.listas.add(e);
	}
	
}
