package es.urjccode.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjccode.models.ListaModel;
import es.urjccode.models.OfertaModel;
import es.urjccode.models.UsuarioModel;

public interface OfertaRepo extends JpaRepository<OfertaModel, Long>{
	
	List<OfertaModel> findByUsuarioCreador(UsuarioModel usuarioCreador);
	
	List<OfertaModel> findByFechaCierreNullAndTituloContainingIgnoreCase(String texto);

}
