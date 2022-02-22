package es.urjccode.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.urjccode.models.ListaModel;
import es.urjccode.repositories.ListaRepo;
import es.urjccode.repositories.UsuarioRepo;

public class ListaController {
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private ListaRepo listaRepo;
	
	@GetMapping("/detalles-lista/{id_lista}")
	public String mostrarDetallesOferta(Model model, @PathVariable Long id_lista) {
		
		ListaModel lista = listaRepo.getById(id_lista);
		model.addAttribute("lista_oferta", lista.getElementos());
		model.addAttribute("nombre", lista.getNombre());
		
		return "template_detalles_lista";
	}
	
	@GetMapping("/cliente")
	public String mostrarListas(Model model) {
		
		List<ListaModel> lista = listaRepo.findAll();
		model.addAttribute("lista", lista);
		
		return "cliente";
	}

}
