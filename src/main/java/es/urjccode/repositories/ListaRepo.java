package es.urjccode.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjccode.models.ListaModel;

public interface ListaRepo extends JpaRepository<ListaModel, Long>{

}
