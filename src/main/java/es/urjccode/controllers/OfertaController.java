package es.urjccode.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjccode.EnumCategorias;
import es.urjccode.models.ListaModel;
import es.urjccode.models.OfertaModel;
import es.urjccode.models.UsuarioModel;
import es.urjccode.models.ValoracionModel;
import es.urjccode.repositories.ListaRepo;
import es.urjccode.repositories.OfertaRepo;
import es.urjccode.repositories.UsuarioRepo;
import es.urjccode.repositories.ValoracionRepo;

@Controller
public class OfertaController {
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private OfertaRepo ofertaRepo;
	
	@Autowired
	private ListaRepo listaRepo;
	
	@Autowired
	private ValoracionRepo valoracionRepo;
	
	@GetMapping(path = {"/buscador-ofertas", ""})
	public String mostrarBuscadorOfertas(Model model) {
		List<OfertaModel> ofertas = ofertaRepo.findAll();
		model.addAttribute("lista_ofertas",ofertas);
		
		return "template_buscador_ofertas";
	}
	
	@PostMapping("/buscador-ofertas")
	public String mostrarBuscadorOfertasFiltrado(Model model,
			@RequestParam("input_texto") String texto) {
		List<OfertaModel> ofertas = ofertaRepo.findByFechaCierreNullAndTituloContainingIgnoreCase(texto);
		model.addAttribute("lista_ofertas",ofertas);

		return "template_buscador_ofertas";
	}
	
	@GetMapping("/detalles-oferta/{id_oferta}")
	public String mostrarDetallesOferta(Model model, @PathVariable Long id_oferta) {
		
		OfertaModel oferta = ofertaRepo.getById(id_oferta);
		model.addAttribute("oferta_seleccionada", oferta);
		model.addAttribute("nombre", oferta.getUsuarioCreador().getNombre());
		
		//usuario a fuego, implementar cuando este control de usuarios
		UsuarioModel usuario =  usuarioRepo.getById((long) 1);
		model.addAttribute("lista_listas", listaRepo.findByfkUsuario(usuario));
		
		return "template_detalles_oferta";
	}
	
	@GetMapping("/crear-oferta")
	public String mostrarCrearOferta(Model model) {
		EnumCategorias[] listaCategorias = EnumCategorias.values();
		List<String> lista = new ArrayList<String>(listaCategorias.length);
		for(int i = 0; i < listaCategorias.length; i++) {
			lista.add(i, listaCategorias[i].toString()); 
		}
		model.addAttribute("categorias", lista);
		
		return "template_crear_oferta";
	}
	
	@PostMapping("/crear-oferta")
	public String crearOferta(Model model,
			@RequestParam double input_precio,
			@RequestParam String input_titulo,
			@RequestParam String input_categoria) {
		
		// TODO: Sustituir usuario grabado a fuego por el usuario que está navegando la página
		OfertaModel ofertaCreada = new OfertaModel(input_precio, input_titulo, EnumCategorias.valueOf(input_categoria), LocalDateTime.now(), usuarioRepo.getById((long) 1));
		ofertaRepo.save(ofertaCreada);
		
		model.addAttribute("informacion", "Su oferta ha sido creada con éxito");
		
		return "template_confirmacion_modificacion_oferta";
	}
	
	@GetMapping("/editar-oferta/{id_oferta}")
	public String mostrarModificarOferta(Model model, @PathVariable Long id_oferta) {
		
		OfertaModel oferta = ofertaRepo.getById(id_oferta);
		
		EnumCategorias[] listaCategorias = EnumCategorias.values();
		List<String> lista = new ArrayList<String>(listaCategorias.length);
		for(int i = 0; i < listaCategorias.length; i++) {
			lista.add(i, listaCategorias[i].toString()); 
		}
		
		model.addAttribute("categorias", lista);
		model.addAttribute("oferta_seleccionada", oferta);
		
		return "template_editar_oferta";
	}
	
	@PostMapping("/modificar-oferta/{id_oferta}")
	public String modificarOferta(Model model,
			@PathVariable Long id_oferta,
			@RequestParam double input_precio,
			@RequestParam String input_titulo,
			@RequestParam String input_categoria) {
		
		OfertaModel oferta = ofertaRepo.getById(id_oferta);
		oferta.actualizar(input_precio, input_titulo, EnumCategorias.valueOf(input_categoria));
		ofertaRepo.save(oferta);
		
		model.addAttribute("informacion", "Su oferta ha sido modificada con exito");
		
		return "template_confirmacion_modificacion_oferta";
	}
	
	@PostMapping("/confirmacion-compra/{id_oferta}")
	public String confirmacionCompra(Model model, 
			@RequestParam String id_oferta) {
		
		Long id = Long.parseLong(id_oferta);
		OfertaModel oferta = ofertaRepo.getById(id);
		model.addAttribute("oferta_seleccionada", oferta);
		
		return "template_confirmacion_compra";
	}
	
	@PostMapping("/comprar-oferta/{id_oferta}")
	public String comprarOferta(Model model, 
			@RequestParam String id_oferta,
			@RequestParam double input_tarjeta) {
		
		Long id = Long.parseLong(id_oferta);
		OfertaModel oferta = ofertaRepo.getById(id);
		model.addAttribute("oferta_seleccionada", oferta);
		
		//usuario a fuego, implementar cuando este control de usuarios
		UsuarioModel usuario =  usuarioRepo.getById((long) 1);
		
		oferta.setUsuarioComprador(usuario);
				
		return "template_valoracion_oferta";
	}
	
	@PostMapping("/valoracion-oferta/{id_oferta}")
	public String valoracionOferta(Model model, 
			@PathVariable Long id_oferta,
			@RequestParam double input_puntuacion,
			@RequestParam String input_comentario) {
		
		OfertaModel oferta = ofertaRepo.getById(id_oferta);
		LocalDateTime fecha_creacion = LocalDateTime.now();
		ValoracionModel valoracion = new ValoracionModel(input_puntuacion, input_comentario, fecha_creacion , oferta);
		valoracionRepo.save(valoracion);
		
		model.addAttribute("informacion", "Gracias por su compra");
		
		return "template_confirmacion_modificacion_oferta";
	}
	
	
	@PostConstruct
	public void init() {
		
		UsuarioModel usu1 = new UsuarioModel("Patxi", "contrasenya", new ArrayList<ListaModel>());
		UsuarioModel usu2 = new UsuarioModel("Pepe", "contrasenya",	new ArrayList<ListaModel>());
		UsuarioModel usu3 = new UsuarioModel("Juan", "contrasenya",	new ArrayList<ListaModel>());
		UsuarioModel usu4 = new UsuarioModel("Javier", "contrasenya", new ArrayList<ListaModel>());
		
		usuarioRepo.saveAll(Arrays.asList(usu1, usu2, usu3, usu4));
		
		OfertaModel oferta3 = new OfertaModel(180.0, "Portatil viejo", EnumCategorias.ELECTRONICA, LocalDateTime.now(), usu1);
		OfertaModel oferta2 = new OfertaModel(57.30, "Zuecos", EnumCategorias.ZAPATILLAS, LocalDateTime.now(), usu3);
		OfertaModel oferta1 = new OfertaModel(5.0, "Casa", EnumCategorias.OTROS, LocalDateTime.now(), usu3);
		OfertaModel oferta4 = new OfertaModel(0.99, "Nemo", EnumCategorias.OTROS, LocalDateTime.now(), usu4);
		
		ofertaRepo.saveAll(Arrays.asList(oferta1, oferta2, oferta3, oferta4));
		
		List<OfertaModel> listadoOfertas = new ArrayList<OfertaModel>();
		ListaModel lista1 = new ListaModel("mi lista", usu1, listadoOfertas);
		lista1.addElemento(oferta4);
		lista1.addElemento(oferta2);
		listaRepo.save(lista1);
		
	}


}