package es.urjccode.controllers;

import java.util.List;

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
import es.urjccode.repositories.ListaRepo;
import es.urjccode.repositories.OfertaRepo;
import es.urjccode.repositories.UsuarioRepo;

@Controller
public class ListaController {
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private ListaRepo listaRepo;
	
	@Autowired
	private OfertaRepo ofertaRepo;
	
	@GetMapping("/detalles-lista/{id_lista}")
	public String mostrarDetallesOferta(Model model, @PathVariable Long id_lista) {
		
		ListaModel lista = listaRepo.getById(id_lista);
		List<OfertaModel> lista_oferta = lista.getElementos();
		model.addAttribute("lista_oferta", lista_oferta);
		model.addAttribute("nombre", lista.getNombre());
		
		return "template_detalles_lista";
	}
	
	@GetMapping("/cliente")
	public String mostrarListas(Model model) {
		
		List<ListaModel> lista = listaRepo.findAll();
		model.addAttribute("lista", lista);
		
		return "cliente";
	}
	
	@PostMapping("/anyadir-lista/{id_oferta}")
	public String modificarOferta(Model model,
			@RequestParam String id_oferta,
			@RequestParam String input_categoria) {
		
		Long id = Long.parseLong(id_oferta);
				
		//usuario a fuego, implementar cuando este control de usuarios usuarios
		UsuarioModel usuario =  usuarioRepo.getById((long) 1);
		List<ListaModel> lista = listaRepo.findByfkUsuarioAndNombre(usuario, input_categoria);
		ListaModel lis = lista.get(0);
		OfertaModel oferta = ofertaRepo.getById(id);
		lis.addElemento(oferta);
		listaRepo.save(lis);
		
		return "template_confirmacion_modificacion_oferta";
	}

}
