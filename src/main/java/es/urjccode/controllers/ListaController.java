package es.urjccode.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/mis-listas")
	public String mostrarListas(Model model) {
		
		List<ListaModel> lista = listaRepo.findAll();
		model.addAttribute("lista", lista);
		
		return "mis_listas";
	}
	
	@PostMapping("/anyadir-lista/{id_oferta}")
	public String modificarOferta(Model model,
			@RequestParam String id_oferta,
			@RequestParam String input_categoria) {
		
		Long id = Long.parseLong(id_oferta);
		OfertaModel oferta = ofertaRepo.getById(id);
				
		//usuario a fuego, implementar cuando este control de usuarios usuarios
		UsuarioModel usuario =  usuarioRepo.getById((long) 1);
		List<ListaModel> lista = listaRepo.findByfkUsuarioAndNombre(usuario, input_categoria);
		ListaModel lis = lista.get(0);
		
		if(lis.buscarOferta(id)) {
			String errorConstruido = "ya tienes el elemento " + oferta.getTitulo() +" en la lista " + lis.getNombre();
			model.addAttribute("error", errorConstruido);
			return "error";
		} else {
			lis.addElemento(oferta);
			listaRepo.save(lis);
			
			model.addAttribute("informacion", "La oferta ha sido añadida con éxito");
			return "template_confirmacion_modificacion_oferta";
		}
		
	}
	
	@GetMapping("/crear-lista")
	public String mostrarCrearLista(Model model) {
		
		return "template_crear_lista";
	}
	
	@PostMapping("/nueva-lista")
	public String crearOferta(Model model, @RequestParam String input_nombre) {
		
		//usuario a fuego, implementar cuando este control de usuarios usuarios
		UsuarioModel usuario =  usuarioRepo.getById((long) 1);
		List<ListaModel> listas_usuario = listaRepo.findByfkUsuarioAndNombre(usuario, input_nombre);
		
		if(listas_usuario.size() == 0) {
			List<OfertaModel> ofertas = new LinkedList<OfertaModel>();
			ListaModel lista = new ListaModel(input_nombre, usuario, ofertas);
			listaRepo.save(lista);
			model.addAttribute("informacion", "La lista ha sido creada con éxito");
			return "template_confirmacion_modificacion_oferta";
		} else {
			model.addAttribute("error", "ya tienes una lista con ese nombre");
			return "error";
		}
		
		
	}
	
	@PostMapping("/panel-usuario/{id}/listas/crear-lista")
	public String crearLista(Model model,
			@PathVariable("id") Long idUsuario,
			@RequestParam("input_nombre") String nombre) {
		
		// TODO: Prever el caso de error en el que ya exista otra lista del mismo nombre para el usuario
		ListaModel nuevaLista = new ListaModel(nombre, usuarioRepo.getById(idUsuario));
		listaRepo.save(nuevaLista);
		
		return "redirect:/";
	}

}
