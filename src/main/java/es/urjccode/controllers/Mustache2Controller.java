package es.urjccode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Mustache2Controller {

	@GetMapping("/musta")
	public String prueba_musta(Model model) {

		model.addAttribute("name", "Mundo 2");
		
		return "musta";
	}
}
