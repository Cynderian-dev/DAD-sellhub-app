package es.urjccode.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjccode.models.UsuarioModel;

public interface UsuarioRepo extends JpaRepository<UsuarioModel, Long>{

}
