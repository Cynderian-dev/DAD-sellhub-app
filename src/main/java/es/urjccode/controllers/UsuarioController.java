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
	
	@GetMapping("/panel-usuario/{id}/informacion")
	public String mostrarInformacionUsuario(Model model, @PathVariable Long id) {
		
		// TODO: Sustituir usuario grabado a fuego por el usuario que está navegando la página
		model.addAttribute("usuario_seleccionado", usuarioRepo.getById((long) 1));
		return "templates_panel_usuario/template_panel_usuario_informacion";
	}
	
	@GetMapping("/panel-usuario/{id}/listas")
	public String mostrarListasUsuario(Model model, @PathVariable Long id) {
		
		// TODO: Sustituir usuario grabado a fuego por el usuario que está navegando la página
		model.addAttribute("usuario_seleccionado", usuarioRepo.getById((long) 1));
		return "templates_panel_usuario/template_panel_usuario_listas";
	}
	
	@GetMapping("/panel-usuario/{id}/ofertas")
	public String mostrarOfertasUsuario(Model model, @PathVariable Long id) {
		
		// TODO: Sustituir usuario grabado a fuego por el usuario que está navegando la página
		model.addAttribute("usuario_seleccionado", usuarioRepo.getById((long) 1));
		return "templates_panel_usuario/template_panel_usuario_ofertas";
	}
	
	@GetMapping("/panel-usuario/{id}/valoraciones")
	public String mostrarValoracionesUsuario(Model model, @PathVariable Long id) {
		
		// TODO: Sustituir usuario grabado a fuego por el usuario que está navegando la página
		model.addAttribute("usuario_seleccionado", usuarioRepo.getById((long) 1));
		return "templates_panel_usuario/template_panel_usuario_valoraciones";
	}
	
	@GetMapping("/buscador-usuarios")
	public String mostrarBuscadorUsuarios(Model model) {
		
		List<UsuarioModel> listaUsuarios = usuarioRepo.findAll();
		model.addAttribute("lista_usuarios", listaUsuarios);
		return "template_buscador_usuarios";
	}

}
