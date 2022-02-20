package es.urjccode.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjccode.EnumCategorias;
import es.urjccode.models.ListaModel;
import es.urjccode.models.OfertaModel;
import es.urjccode.models.UsuarioModel;
import es.urjccode.repositories.OfertaRepo;
import es.urjccode.repositories.UsuarioRepo;

@Controller
public class OfertaController {
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private OfertaRepo ofertaRepo;
	
	@GetMapping(path = {"/buscador-ofertas", ""})
	public String mostrarBuscadorOfertas(Model model) {
		List<OfertaModel> ofertas = ofertaRepo.findAll();
		model.addAttribute("lista_ofertas",ofertas);
		
		return "template_buscador_ofertas";
	}
	
	@GetMapping("/detalles-oferta/{id_oferta}")
	public String mostrarDetallesOferta(Model model, @PathVariable Long id_oferta) {
		
		OfertaModel oferta = ofertaRepo.getById(id_oferta);
		model.addAttribute("oferta_seleccionada", oferta);
		model.addAttribute("nombre", oferta.getUsuario_creador().getNombre());
		
		return "template_detalles_oferta";
	}
	
	@GetMapping("/crear-oferta")
	public String mostrarCrearOferta(Model model) {
		
		
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
		
		return "redirect:/buscador-ofertas";
	}
	
	@GetMapping("/editar-oferta/{id_oferta}")
	public String editarOferta(Model model, @PathVariable Long id_oferta) {
		
		OfertaModel oferta = ofertaRepo.getById(id_oferta);
		model.addAttribute("oferta_seleccionada", oferta);
		
		return "template_editar_oferta";
	}
	
	@PostMapping("/modificar-oferta/{id_oferta}")
	public String modificarOferta(Model model,
			@PathVariable Long id_oferta,
			@RequestParam double input_precio,
			@RequestParam String input_titulo,
			@RequestParam String input_categoria) {
		
		// TODO: Sustituir usuario grabado a fuego por el usuario que está navegando la página
		OfertaModel oferta = ofertaRepo.getById(id_oferta);
		oferta.actualizar(input_precio, input_titulo, EnumCategorias.valueOf(input_categoria));
		ofertaRepo.save(oferta);
		
		return "redirect:/buscador-ofertas";
	}
	
	@GetMapping("/confirmacion-compra/{id_oferta}")
	public String confirmacionCompra(Model model, @PathVariable Long id_oferta) {
		
		return "template_confirmacion_compra";
	}
	
	@PostMapping("/comprar-oferta/{id_oferta}")
	public String comprarOferta(Model model, @PathVariable Long id_oferta) {
		
		return "template_valoracion_oferta";
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
		
	}


}