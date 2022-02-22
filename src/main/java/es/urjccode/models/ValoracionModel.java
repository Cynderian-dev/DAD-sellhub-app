package es.urjccode.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ValoracionModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Float puntuacion;
	private String comentario;
	private LocalDateTime fechaCreacion;
	@OneToOne
	private OfertaModel fkOferta;
	
	public ValoracionModel() {
		
	}
	
	public ValoracionModel(Float puntuacion, String comentario, LocalDateTime fecha_creacion, OfertaModel fk_oferta) {
		super();
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.fechaCreacion = fecha_creacion;
		this.fkOferta = fk_oferta;
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

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fecha_creacion) {
		this.fechaCreacion = fecha_creacion;
	}

	public OfertaModel getFkOferta() {
		return fkOferta;
	}

	public void setFkOferta(OfertaModel fk_oferta) {
		this.fkOferta = fk_oferta;
	}
	
	

}
