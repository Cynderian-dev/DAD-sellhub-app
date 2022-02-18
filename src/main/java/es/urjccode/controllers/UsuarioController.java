package es.urjccode.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.urjccode.models.UsuarioModel;
import es.urjccode.repositories.UsuarioRepo;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@GetMapping("/panel-usuario")
	public String mostrarPanelUsuarioPersonal(Model model) {
		
		// TODO: Sustituir usuario grabado a fuego por el usuario que está navegando la página
		model.addAttribute("usuario_seleccionado", usuarioRepo.getById((long) 1));
		return "template_panel_usuario";
	}
	
	@GetMapping("/panel-usuario/{id}")
	public String mostrarPanelUsuario(Model model, @PathVariable Long id) {
		
		model.addAttribute("usuario_seleccionado", usuarioRepo.getById(id));
		return "template_panel_usuario";
	}
	
	@GetMapping("/buscador-usuarios")
	public String mostrarBuscadorUsuarios(Model model) {
		
		List<UsuarioModel> listaUsuarios = usuarioRepo.findAll();
		model.addAttribute("lista_usuarios", listaUsuarios);
		return "template_buscador_usuarios";
	}

}
