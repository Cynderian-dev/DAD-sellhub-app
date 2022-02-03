package es.urjccode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MustacheController {
	@GetMapping("/mustache")
	public String prueba_mustache(Model model) {

		model.addAttribute("name", "Mundo");
		
		return "mustache";
	}
}
