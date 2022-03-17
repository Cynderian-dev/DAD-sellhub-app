package es.urjccode.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

import javax.servlet.http.HttpServletRequest;

@Controller
public class ListaController {
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private ListaRepo listaRepo;
	
	@Autowired
	private OfertaRepo ofertaRepo;
	
	@GetMapping("/detalles-lista/{id_lista}")
	public String mostrarDetallesOferta(Model model, HttpServletRequest request, @PathVariable Long id_lista) {

		if (request.getUserPrincipal() == null) {
			model.addAttribute("id_usuario_activo", null);
		} else {
			UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
					.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
			model.addAttribute("id_usuario_activo", usuarioActivo.getId());
		}

		
		ListaModel lista = listaRepo.getById(id_lista);
		List<OfertaModel> lista_oferta = lista.getElementos();
		model.addAttribute("lista_oferta", lista_oferta);
		model.addAttribute("id_lista", lista.getId());
		model.addAttribute("nombre", lista.getNombre());
		
		return "template_detalles_lista";
	}
	
	@PostMapping("/anyadir-lista/{id_oferta}")
	public String modificarOferta(Model model,
			HttpServletRequest request,
			@RequestParam String id_oferta,
			@RequestParam String input_categoria) {
		
		Long id = Long.parseLong(id_oferta);
		OfertaModel oferta = ofertaRepo.getById(id);
		UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
				
		//TODO: usuario a fuego, implementar cuando este control de usuarios usuarios
		
		UsuarioModel usuario =  usuarioRepo.getById(usuarioActivo.getId());
		List<ListaModel> lista = listaRepo.findByfkUsuarioAndNombre(usuario, input_categoria);
		ListaModel lis = lista.get(0);
		
		if(lis.buscarOferta(id)) {
			String errorConstruido = "ya tienes el elemento " + oferta.getTitulo() +" en la lista " + lis.getNombre();
			model.addAttribute("error", errorConstruido);
			return "template_info_error";
		} else {
			lis.addElemento(oferta);
			listaRepo.save(lis);
			oferta.addElemento(lis);
			ofertaRepo.save(oferta);
			
			model.addAttribute("informacion", "La oferta ha sido añadida con éxito");
			return "template_confirmacion_modificacion_oferta";
		}
		
	}
	
	@PostMapping("/borrar-lista/{id}")
	public String borrarLista(Model model,
			@RequestParam String id) {
		
		long id_lis = Long.parseLong(id);
		
		ListaModel lista = listaRepo.getById(id_lis);
		
		List<OfertaModel> lista_ofertas = lista.getElementos();
		for (OfertaModel o:lista_ofertas) {
			o.removeElemento(lista);
			ofertaRepo.save(o);
		}
		
		lista.setElementos(new LinkedList<OfertaModel>());
		listaRepo.save(lista);
		listaRepo.delete(lista);
		
		model.addAttribute("informacion", "La lista ha sido borrada con éxito");
		return "template_confirmacion_modificacion_oferta";
	}
	
	@PostMapping("/borrar-elemento/{id_oferta}")
	public String borrarElemento(Model model,
			@RequestParam String id_oferta,
			@RequestParam String id_lista) {
		
		long id_lis = Long.parseLong(id_lista);
		long id_of = Long.parseLong(id_oferta);
		
		ListaModel lista = listaRepo.getById(id_lis);
		OfertaModel oferta = ofertaRepo.getById(id_of);
		
		oferta.removeElemento(lista);
		ofertaRepo.save(oferta);
		lista.removeElemento(oferta);
		listaRepo.save(lista);
		
		model.addAttribute("informacion", "El elemento ha sido borrado con éxito");
		return "template_confirmacion_modificacion_oferta";
	}

	@PostMapping("/panel-usuario/{id}/listas/crear-lista")
	public String crearLista(
			Model model,
			HttpServletRequest request,
			@PathVariable("id") Long idUsuarioPanel,
			@RequestParam("input_nombre") String nombre) {

		UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));

		// Comprueba si ya existe una lista del mismo nombre
		List<ListaModel> listasDeMismoNombre = listaRepo.findByfkUsuarioAndNombre(usuarioActivo, nombre);

		if(listasDeMismoNombre.size() == 0) {
			ListaModel lista = new ListaModel(nombre, usuarioActivo);
			listaRepo.save(lista);

			model.addAttribute("informacion", "La lista ha sido creada con éxito");

			return "redirect:/panel-usuario/" + idUsuarioPanel.toString() + "/listas";
		} else {
			model.addAttribute("error", "ya tienes una lista con ese nombre");

			return "template_info_error";
		}

	}

//	@PostMapping("/crear-lista")
//	public String crearLista(Model model, @RequestParam String input_nombre, HttpServletRequest request) {
//
//		UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
//				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
//
//		List<ListaModel> listas_usuario = listaRepo.findByfkUsuarioAndNombre(usuarioActivo, input_nombre);
//
//		if(listas_usuario.size() == 0) {
//			List<OfertaModel> ofertas = new LinkedList<OfertaModel>();
//			ListaModel lista = new ListaModel(input_nombre, usuario, ofertas);
//			listaRepo.save(lista);
//			model.addAttribute("informacion", "La lista ha sido creada con éxito");
//			return "template_confirmacion_modificacion_oferta";
//		} else {
//			model.addAttribute("error", "ya tienes una lista con ese nombre");
//			return "template_info_error";
//		}
//
//	}
	


}
