package es.urjccode.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import es.urjccode.services.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjccode.EnumCategorias;
import es.urjccode.models.ListaModel;
import es.urjccode.models.OfertaModel;
import es.urjccode.models.UsuarioModel;
import es.urjccode.repositories.ListaRepo;
import es.urjccode.repositories.OfertaRepo;
import es.urjccode.repositories.UsuarioRepo;

@Controller
public class OfertaController {

	@Autowired
	private OfertaService ofertaService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private OfertaRepo ofertaRepo;
	
	@Autowired
	private ListaRepo listaRepo;


	@GetMapping(path = {"/buscador-ofertas", ""})
	public String mostrarBuscadorOfertas(Model model, HttpServletRequest request) {

		if (request.getUserPrincipal() == null) {
			model.addAttribute("id_usuario_activo", null);
		} else {
			UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
					.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
			model.addAttribute("id_usuario_activo", usuarioActivo.getId());
		}

		List<OfertaModel> ofertas = ofertaService.obtenerOferta();
		model.addAttribute("lista_ofertas",ofertas);
		
		// Le paso los valores del enum al modelo
		EnumCategorias[] listaCategorias = EnumCategorias.values();
		List<String> lista = new ArrayList<>(listaCategorias.length);
		for(int i = 0; i < listaCategorias.length; i++) {
			lista.add(i, listaCategorias[i].toString()); 
		}
		model.addAttribute("categorias", lista);
		
		return "template_buscador_ofertas";
	}



	
	@PostMapping("/buscador-ofertas")
	public String mostrarBuscadorOfertasFiltrado(Model model,
			@RequestParam("input_texto") String texto,
			@RequestParam("input_precio") String precioMaximo,
			@RequestParam("input_categoria") String categoria) {
		
		
		List<OfertaModel> ofertas;
		
		if (Objects.equals(precioMaximo, "") && categoria.equals("TODAS")) {
			// B??squeda sin filtros (s??lo el filtro de t??tulo)
			ofertas = ofertaRepo.findByFechaCierreNullAndTituloContainingIgnoreCaseOrderByPrecio(texto);
		} else if (Objects.equals(precioMaximo, "") && !categoria.equals("TODAS")) {
			// B??squeda filtrada por categor??a
			ofertas = ofertaRepo.findByFechaCierreNullAndTituloContainingIgnoreCaseAndCategoriaEqualsOrderByPrecio(texto, EnumCategorias.valueOf(categoria));
		} else if (!Objects.equals(precioMaximo, "") && categoria.equals("TODAS")) {
			// B??squeda filtrada por precio
			ofertas = ofertaRepo.findByFechaCierreNullAndTituloContainingIgnoreCaseAndPrecioLessThanOrderByPrecio(texto, Double.parseDouble(precioMaximo));
		} else {
			// B??squeda filtrada por precio Y categor??a
			ofertas = ofertaRepo.findByFechaCierreNullAndTituloContainingIgnoreCaseAndPrecioLessThanAndCategoriaEqualsOrderByPrecio(texto, Double.parseDouble(precioMaximo), EnumCategorias.valueOf(categoria));
		}
		
		EnumCategorias[] listaCategorias = EnumCategorias.values();
		List<String> lista = new ArrayList<>(listaCategorias.length);
		for(int i = 0; i < listaCategorias.length; i++) {
			lista.add(i, listaCategorias[i].toString()); 
		}
		model.addAttribute("categorias", lista);
		
		model.addAttribute("lista_ofertas", ofertas);
		return "template_buscador_ofertas";
	}
	
	@GetMapping("/detalles-oferta/{id_oferta}")
	public String mostrarDetallesOferta(Model model, HttpServletRequest request, @PathVariable Long id_oferta) {

		OfertaModel oferta = ofertaRepo.getById(id_oferta);

		if (request.getUserPrincipal() == null) {
			model.addAttribute("id_usuario_activo", null);
		} else {
			UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
					.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
			model.addAttribute("id_usuario_activo", usuarioActivo.getId());

			if (usuarioActivo.getId() == oferta.getUsuarioCreador().getId()) {
				if(oferta.getUsuarioComprador() == null) {
					model.addAttribute("borrado", true);
				} else {
					model.addAttribute("borrado", false);
				}
			} else {
				model.addAttribute("borrado", false);
			}

			model.addAttribute("lista_listas", listaRepo.findByfkUsuario(usuarioActivo));
		}
		

		model.addAttribute("oferta_seleccionada", oferta);
		model.addAttribute("nombre", oferta.getUsuarioCreador().getNombre());
		

		
		if(oferta.getUsuarioComprador() == null) {
			model.addAttribute("mostrar", true);
		} else {
			model.addAttribute("mostrar", false);
		}
		
		//esto seria para que no se le mostrase el boton comprar al usuario creador de la oferta
		/*if (oferta.getUsuarioCreador().getId() == usuario.getId()) {
			model.addAttribute("mostrar", false);
		} else {
			model.addAttribute("mostrar", true);EnumCategorias[] listaCategorias = EnumCategorias.values();
		List<String> lista = new ArrayList<String>(listaCategorias.length);
		for(int i = 0; i < listaCategorias.length; i++) {
			lista.add(i, listaCategorias[i].toString()); 
		}
		model.addAttribute("categorias", lista);
		}*/
		
		

		
		return "template_detalles_oferta";
	}
	
	@GetMapping("/crear-oferta")
	public String mostrarCrearOferta(Model model, HttpServletRequest request) {

		if (request.getUserPrincipal() == null) {
			model.addAttribute("id_usuario_activo", null);
		} else {
			UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
					.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
			model.addAttribute("id_usuario_activo", usuarioActivo.getId());
		}

		EnumCategorias[] listaCategorias = EnumCategorias.values();
		List<String> lista = new ArrayList<>(listaCategorias.length);
		for(int i = 0; i < listaCategorias.length; i++) {
			lista.add(i, listaCategorias[i].toString()); 
		}
		model.addAttribute("categorias", lista);
		
		return "template_crear_oferta";
	}
	
	@CacheEvict(cacheNames="ofertas", allEntries=true)
	@PostMapping("/crear-oferta")
	public String crearOferta(Model model,
			HttpServletRequest request,
			@RequestParam double input_precio,
			@RequestParam String input_titulo,
			@RequestParam String input_categoria) {

		UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));

		OfertaModel ofertaCreada = new OfertaModel(input_precio, input_titulo, EnumCategorias.valueOf(input_categoria), LocalDateTime.now(), usuarioRepo.getById(usuarioActivo.getId()));
		ofertaRepo.save(ofertaCreada);
		
		model.addAttribute("informacion", "Su oferta ha sido creada con ??xito");
		
		return "template_confirmacion_modificacion_oferta";
	}
	
	@PostMapping("/editar-oferta/{id_oferta}")
	public String mostrarModificarOferta(Model model,
			HttpServletRequest request,
			@RequestParam String id_oferta) {

		UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
		
		Long id = Long.parseLong(id_oferta);
		OfertaModel oferta = ofertaRepo.getById(id);
				
		if(oferta.getUsuarioCreador().getId() != usuarioActivo.getId()) {
			model.addAttribute("error", "No es tu oferta");
			return "template_info_error";
		}
				
		if(oferta.getUsuarioComprador() != null) {
			model.addAttribute("error", "Esta oferta ya ha sido vendida, no puede ser borrada");
			return "template_info_error";
		}
		
		EnumCategorias[] listaCategorias = EnumCategorias.values();
		List<String> lista = new ArrayList<String>(listaCategorias.length);
		for(int i = 0; i < listaCategorias.length; i++) {
			lista.add(i, listaCategorias[i].toString()); 
		}
		
		model.addAttribute("categorias", lista);
		model.addAttribute("oferta_seleccionada", oferta);
		
		return "template_editar_oferta";
	}
	
	@CacheEvict(cacheNames="ofertas", allEntries=true)
	@PostMapping("/modificar-oferta/{id_oferta}")
	public String modificarOferta(Model model,
			@PathVariable Long id_oferta,
			@RequestParam double input_precio,
			@RequestParam String input_titulo,
			@RequestParam String input_categoria) {
		
		OfertaModel oferta = ofertaRepo.getById(id_oferta);
		oferta.actualizar(input_precio, input_titulo, EnumCategorias.valueOf(input_categoria));
		ofertaRepo.save(oferta);
		
		model.addAttribute("informacion", "Su oferta ha sido modificada con exito");
		
		return "template_confirmacion_modificacion_oferta";
	}
	
	@PostMapping("/confirmacion-compra/{id_oferta}")
	public String confirmacionCompra(Model model, 
			@RequestParam String id_oferta) {
		
		Long id = Long.parseLong(id_oferta);
		OfertaModel oferta = ofertaRepo.getById(id);
		model.addAttribute("oferta_seleccionada", oferta);
		
		return "template_confirmacion_compra";
	}
	
	@CacheEvict(cacheNames="ofertas", allEntries=true)
	@PostMapping("/comprar-oferta/{id_oferta}")
	public String comprarOferta(Model model,
			HttpServletRequest request,
			@RequestParam String id_oferta,
			@RequestParam double input_tarjeta) {

		UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
		
		Long id = Long.parseLong(id_oferta);
		OfertaModel oferta = ofertaRepo.getById(id);
		
		oferta.setUsuarioComprador(usuarioActivo);
		LocalDateTime fecha_cierre = LocalDateTime.now();
		oferta.setFechaCierre(fecha_cierre);
		ofertaRepo.save(oferta);
		model.addAttribute("oferta_seleccionada", oferta);
				
		return "template_valoracion_oferta";
	}
	
	@CacheEvict(cacheNames="ofertas", allEntries=true)
	@PostMapping("/borrar-oferta/{id}")
	public String borrarOferta(Model model,
			HttpServletRequest request,
			@RequestParam String id_oferta) {

		UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName())
				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario"));
		
		long id_ofer = Long.parseLong(id_oferta);
		
		OfertaModel oferta = ofertaRepo.getById(id_ofer);
		
		if(oferta.getUsuarioCreador().getId() != usuarioActivo.getId()) {
			model.addAttribute("error", "No es tu oferta");
			return "template_info_error";
		}
		
		if(oferta.getUsuarioComprador() != null) {
			model.addAttribute("error", "Esta oferta ya ha sido vendida, no puede ser borrada");
			return "template_info_error";
		}
		
		List<ListaModel> lista_listas = oferta.getListas();
		for (ListaModel l:lista_listas) {
			l.removeElemento(oferta);
			listaRepo.save(l);
		}
		
		oferta.setListas(new LinkedList<ListaModel>());
		ofertaRepo.save(oferta);
		ofertaRepo.delete(oferta);
		
		model.addAttribute("informacion", "La oferta ha sido borrado con ??xito");
		return "template_confirmacion_modificacion_oferta";
	}
	
	//@PostConstruct
	public void init() {

		String correoTests = "serginmatonis@gmail.com";

		UsuarioModel usu1 = new UsuarioModel("Patxi", passwordEncoder.encode("pass"), correoTests);
		UsuarioModel usu2 = new UsuarioModel("Pepe", passwordEncoder.encode("pass"), correoTests);
		UsuarioModel usu3 = new UsuarioModel("Juan", passwordEncoder.encode("pass"), correoTests);
		UsuarioModel usu4 = new UsuarioModel("Javier", passwordEncoder.encode("pass"), correoTests);
		
		usuarioRepo.saveAll(Arrays.asList(usu1, usu2, usu3, usu4));
		
		OfertaModel oferta3 = new OfertaModel(180.0, "Portatil viejo", EnumCategorias.ELECTRONICA, LocalDateTime.now(), usu1);
		OfertaModel oferta2 = new OfertaModel(57.30, "Zuecos", EnumCategorias.ZAPATILLAS, LocalDateTime.now(), usu3);
		OfertaModel oferta1 = new OfertaModel(5.0, "Casa", EnumCategorias.OTROS, LocalDateTime.now(), usu3);
		OfertaModel oferta4 = new OfertaModel(0.99, "Nemo", EnumCategorias.OTROS, LocalDateTime.now(), usu4);
		
		ofertaRepo.saveAll(Arrays.asList(oferta1, oferta2, oferta3, oferta4));
		
		List<OfertaModel> listadoOfertas = new ArrayList<OfertaModel>();
		ListaModel lista1 = new ListaModel("mi lista", usu1, listadoOfertas);
		lista1.addElemento(oferta4);
		lista1.addElemento(oferta2);
		listaRepo.save(lista1);
		
	}


}