package es.urjccode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OfertaController {
	
	@GetMapping("/buscador-ofertas")
	public String mostrarBuscadorOfertas(Model model) {
		
		
		return "template_buscador_ofertas";
	}
	
	@GetMapping("/detalles-oferta/{id}")
	public String mostrarDetallesOferta(Model model, @PathVariable Long id_oferta) {
		
		
		return "template_detalles_oferta";
	}
	
	@GetMapping("/crear-oferta")
	public String mostrarCrearOferta(Model model) {
		
		
		return "template_crear_oferta";
	}

}