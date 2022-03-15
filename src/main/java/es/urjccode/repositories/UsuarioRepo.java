package es.urjccode.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjccode.models.UsuarioModel;

public interface UsuarioRepo extends JpaRepository<UsuarioModel, Long>{

	List<UsuarioModel> findByNombreContainingIgnoreCase(String texto);
	
	//TODO: a lo mejor podemos quitar este
	List<UsuarioModel> findByNombreAndContrasenya(String nombre, String contrasenya);
	
	Optional<UsuarioModel> findByNombre(String nombre);

}
