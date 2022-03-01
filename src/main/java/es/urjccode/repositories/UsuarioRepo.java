package es.urjccode.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjccode.models.UsuarioModel;

public interface UsuarioRepo extends JpaRepository<UsuarioModel, Long>{

	List<UsuarioModel> findByNombreContainingIgnoreCase(String texto);
	
	List<UsuarioModel> findByNombreAndContrasenya(String nombre, String contrasenya);

}
