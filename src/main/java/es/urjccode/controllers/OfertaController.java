package es.urjccode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OfertaController {
	
	@GetMapping(path = {"/buscador-ofertas", ""})
	public String mostrarBuscadorOfertas(Model model) {
		
		
		return "template_buscador_ofertas";
	}
	
	@GetMapping("/detalles-oferta/{id_oferta}")
	public String mostrarDetallesOferta(Model model, @PathVariable Long id_oferta) {
		
		
		return "template_detalles_oferta";
	}
	
	@GetMapping("/crear-oferta")
	public String mostrarCrearOferta(Model model) {
		
		
		return "template_crear_oferta";
	}
	
	@PostMapping("/crear-oferta")
	public String crearOferta(Model model) {
		
		
		return "template_crear_oferta";
	}


}