package es.urjccode.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjccode.models.UsuarioModel;
import es.urjccode.models.ValoracionModel;

public interface ValoracionRepo extends JpaRepository<ValoracionModel, Long>{
	
	List<ValoracionModel> findByfkOfertaUsuarioCreador(UsuarioModel usuarioCreador);
}
