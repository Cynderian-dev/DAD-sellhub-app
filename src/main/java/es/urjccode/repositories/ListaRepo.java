package es.urjccode.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjccode.models.ListaModel;
import es.urjccode.models.UsuarioModel;

public interface ListaRepo extends JpaRepository<ListaModel, Long>{
	
	List<ListaModel> findByfkUsuario(UsuarioModel fkUsuario);

}
