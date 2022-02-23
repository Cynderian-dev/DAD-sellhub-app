package es.urjccode.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjccode.EnumCategorias;
import es.urjccode.models.OfertaModel;
import es.urjccode.models.UsuarioModel;

public interface OfertaRepo extends JpaRepository<OfertaModel, Long>{
	
	List<OfertaModel> findByUsuarioCreador(UsuarioModel usuarioCreador);
	
	List<OfertaModel> findByFechaCierreNullOrderByPrecio();
	
	List<OfertaModel> findByFechaCierreNullAndTituloContainingIgnoreCaseOrderByPrecio(String texto);
	
	List<OfertaModel> findByFechaCierreNullAndTituloContainingIgnoreCaseAndPrecioLessThanOrderByPrecio(String texto, double precioMaximo);

	List<OfertaModel> findByFechaCierreNullAndTituloContainingIgnoreCaseAndCategoriaEqualsOrderByPrecio(String texto,
			EnumCategorias categoria);

	List<OfertaModel> findByFechaCierreNullAndTituloContainingIgnoreCaseAndPrecioLessThanAndCategoriaEqualsOrderByPrecio(
			String texto, double precioMaximo, EnumCategorias categoria);

}
