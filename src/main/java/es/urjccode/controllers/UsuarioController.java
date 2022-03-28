package es.urjccode.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import javax.servlet.http.HttpServletRequest;

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
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	private void prepararBarraNavegacionUsuario(Model model, Long idPanel, HttpServletRequest request) {
		// Decide si mostrar la opción "logout" en la barra de navegación del panel de usuario
		// Si el panel de usuario es el del usuario que está navegando, se muestra la opción "logout"

		if (request.getUserPrincipal() == null) {
			model.addAttribute("id_usuario_activo", null);
			model.addAttribute("es_propietario_panel", false);
		} else {
			UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
					.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
			model.addAttribute("id_usuario_activo", usuarioActivo.getId());

			if (Objects.equals(usuarioActivo.getId(), idPanel)) {
				model.addAttribute("es_propietario_panel", true);
			} else {
				model.addAttribute("es_propietario_panel", false);
			}
		}







	}
	

	@GetMapping("/login")
	public String login() {
		return "template_login";
	}

	@GetMapping("/logout-done")
	public String logoutDone() {
		return "template_logout_completado";
	}

	@GetMapping("/login-error")
	public String loginError() {
		return "template_error_login";
	}

	@GetMapping("/usuario-creado")
	public String usuarioCreado() {
		return "template_creacion_usuario_completada";
	}

	@GetMapping("/crear-usuario")
	public String mostrarCrearUsuario() {
		return "template_crear_usuario";
	}
	
	@PostMapping("/crear-usuario")
	public String crearUsuario(Model model,
							   @RequestParam("input_usuario") String usuario,
							   @RequestParam("input_contrasenya") String contrasenya,
							   @RequestParam("input_correo") String correo) {
		
		UsuarioModel nuevoUsu = new UsuarioModel(usuario, passwordEncoder.encode(contrasenya), correo);
		usuarioRepo.save(nuevoUsu);
		model.addAttribute("informacion", "El usuario ha sido creado correctamente");
		return "template_creacion_usuario_completada";
	}

	@GetMapping("/buscador-usuarios")
	public String mostrarBuscadorUsuarios(Model model, HttpServletRequest request) {

		if (request.getUserPrincipal() == null) {
			model.addAttribute("id_usuario_activo", null);
		} else {
			UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
					.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
			model.addAttribute("id_usuario_activo", usuarioActivo.getId());
		}

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

	@GetMapping("/panel-usuario/{id}/informacion")
	public String mostrarInformacionUsuario(Model model,HttpServletRequest request, @PathVariable Long id) {
		prepararBarraNavegacionUsuario(model, id, request);

		// TODO: Sustituir usuario grabado a fuego por el usuario que está navegando la página
		model.addAttribute("usuario_seleccionado", usuarioRepo.getById(id));
		return "templates_panel_usuario/template_panel_usuario_informacion";
	}

	@GetMapping("/panel-usuario/{id}/listas")
	public String mostrarListasUsuario(Model model, @PathVariable Long id, HttpServletRequest request) {
		prepararBarraNavegacionUsuario(model, id, request);
		
		// TODO: Sustituir usuario grabado a fuego por el usuario que está navegando la página
		UsuarioModel usuarioSeleccionado = usuarioRepo.getById(id);
		
		model.addAttribute("usuario_seleccionado", usuarioSeleccionado);
		model.addAttribute("lista_listas", listaRepo.findByfkUsuario(usuarioSeleccionado));
		return "templates_panel_usuario/template_panel_usuario_listas";
	}
	
	@GetMapping("/panel-usuario/{id}/ofertas")
	public String mostrarOfertasUsuario(Model model, @PathVariable Long id, HttpServletRequest request) {
		prepararBarraNavegacionUsuario(model, id, request);
		
		UsuarioModel usuarioSeleccionado = usuarioRepo.getById(id);
		
		model.addAttribute("usuario_seleccionado", usuarioSeleccionado);
		model.addAttribute("lista_ofertas", ofertaRepo.findByUsuarioCreador(usuarioSeleccionado));
		
		return "templates_panel_usuario/template_panel_usuario_ofertas";
	}
	
	@GetMapping("/panel-usuario/{id}/valoraciones")
	public String mostrarValoracionesUsuario(Model model, @PathVariable Long id, HttpServletRequest request) {
		prepararBarraNavegacionUsuario(model, id, request);
		
		UsuarioModel usuarioSeleccionado = usuarioRepo.getById(id);
		
		model.addAttribute("usuario_seleccionado", usuarioSeleccionado);
		model.addAttribute("lista_valoraciones", valoracionRepo.findByfkOfertaUsuarioCreador(usuarioSeleccionado));
		
		return "templates_panel_usuario/template_panel_usuario_valoraciones";
	}


}
