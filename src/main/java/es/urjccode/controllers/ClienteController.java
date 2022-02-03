package es.urjccode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {
	
	@GetMapping("/cliente")
	public String cliente(Model model) {
		
		model.addAttribute("name", "cliente");
		
		return "cliente";
	}

}
