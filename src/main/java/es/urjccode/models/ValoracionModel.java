package es.urjccode.models;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class ValoracionModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Float puntuacion;
	private String comentario;
	private LocalDateTime fecha_creacion;
	@OneToOne
	private OfertaModel fk_oferta;
	
	public ValoracionModel() {
		
	}
	
	public ValoracionModel(Float puntuacion, String comentario, LocalDateTime fecha_creacion, OfertaModel fk_oferta) {
		super();
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.fecha_creacion = fecha_creacion;
		this.fk_oferta = fk_oferta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Float puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDateTime getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDateTime fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public OfertaModel getFk_oferta() {
		return fk_oferta;
	}

	public void setFk_oferta(OfertaModel fk_oferta) {
		this.fk_oferta = fk_oferta;
	}
	
	

}
