package es.urjccode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {
	
	@GetMapping("/panel-usuario")
	public String mostrarPanelUsuario() {
		
		return "template_panel_usuario";
	}
	
	@GetMapping("/buscador-usuarios")
	public String mostrarBuscadorUsuarios() {
		
		return "template_buscador_usuarios";
	}

}
