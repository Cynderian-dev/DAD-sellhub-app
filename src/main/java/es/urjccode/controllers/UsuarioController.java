package es.urjccode.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjccode.models.UsuarioModel;
import es.urjccode.repositories.ListaRepo;
import es.urjccode.repositories.OfertaRepo;
import es.urjccode.repositories.UsuarioRepo;
import es.urjccode.repositories.ValoracionRepo;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private ListaRepo listaRepo;
	
	@Autowired
	private OfertaRepo ofertaRepo;
	
	@Autowired
	private ValoracionRepo valoracionRepo;
	
	@GetMapping("/panel-usuario/{id}/informacion")
	public String mostrarInformacionUsuario(Model model, @PathVariable Long id) {
		
		// TODO: Sustituir usuario grabado a fuego por el usuario que está navegando la página
		model.addAttribute("usuario_seleccionado", usuarioRepo.getById((long) 1));
		return "templates_panel_usuario/template_panel_usuario_informacion";
	}
	
	@GetMapping("/panel-usuario/{id}/listas")
	public String mostrarListasUsuario(Model model, @PathVariable Long id) {
		
		UsuarioModel usuarioSeleccionado = usuarioRepo.getById(id);
		
		model.addAttribute("usuario_seleccionado", usuarioSeleccionado);
		model.addAttribute("lista_listas", listaRepo.findByfkUsuario(usuarioSeleccionado));
		return "templates_panel_usuario/template_panel_usuario_listas";
	}
	
	@GetMapping("/panel-usuario/{id}/ofertas")
	public String mostrarOfertasUsuario(Model model, @PathVariable Long id) {
		
		UsuarioModel usuarioSeleccionado = usuarioRepo.getById(id);
		
		model.addAttribute("usuario_seleccionado", usuarioSeleccionado);
		model.addAttribute("lista_ofertas", ofertaRepo.findByUsuarioCreador(usuarioSeleccionado));
		
		return "templates_panel_usuario/template_panel_usuario_ofertas";
	}
	
	@GetMapping("/panel-usuario/{id}/valoraciones")
	public String mostrarValoracionesUsuario(Model model, @PathVariable Long id) {
		
		UsuarioModel usuarioSeleccionado = usuarioRepo.getById(id);
		
		model.addAttribute("usuario_seleccionado", usuarioSeleccionado);
		model.addAttribute("lista_valoraciones", valoracionRepo.findByfkOfertaUsuarioCreador(usuarioSeleccionado));
		
		return "templates_panel_usuario/template_panel_usuario_valoraciones";
	}
	
	@GetMapping("/buscador-usuarios")
	public String mostrarBuscadorUsuarios(Model model) {
		
		List<UsuarioModel> listaUsuarios = usuarioRepo.findAll();
		model.addAttribute("lista_usuarios", listaUsuarios);
		return "template_buscador_usuarios";
	}
	
	@PostMapping("/buscador-usuarios")
	public String mostrarBuscadorUsuariosFiltrado(Model model, @RequestParam("input_texto") String texto) {
		
		// TODO: filtrar con texto
		List<UsuarioModel> listaUsuarios = usuarioRepo.findByNombreContainingIgnoreCase(texto);
		model.addAttribute("lista_usuarios", listaUsuarios);
		return "template_buscador_usuarios";
	}

}
