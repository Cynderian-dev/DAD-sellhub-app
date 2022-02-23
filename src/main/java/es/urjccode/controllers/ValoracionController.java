package es.urjccode.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjccode.models.OfertaModel;
import es.urjccode.models.ValoracionModel;
import es.urjccode.repositories.OfertaRepo;
import es.urjccode.repositories.ValoracionRepo;

@Controller
public class ValoracionController {
	
	@Autowired
	private ValoracionRepo valoracionRepo;
	
	@Autowired
	private OfertaRepo ofertaRepo;
	
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

}
