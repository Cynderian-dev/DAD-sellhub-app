package es.urjccode.controllers;

import es.urjccode.models.UsuarioModel;
import es.urjccode.repositories.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CorreoController {

    @Autowired
    private UsuarioRepo usuarioRepo;

    // Muestra el formulario para el envío de mensajes
    @GetMapping("/enviar-correo/{nombre_usuario_destino}")
    public String mostrarEnviarCorreo(Model model,
                                      HttpServletRequest request,
                                      @PathVariable("nombre_usuario_destino") String nombreUsuarioDestino) {

        UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName()).orElseThrow();
        UsuarioModel destinatario = usuarioRepo.findByNombre(nombreUsuarioDestino).orElseThrow();

        model.addAttribute("usuario_destino", destinatario);


        return "template_enviar_mensaje";
    }

    @PostMapping("/enviar-correo/{nombre_usuario_destino}")
    public String postEnviarCorreo(Model model,
                                   HttpServletRequest request,
                                   @PathVariable("nombre_usuario_destino") String nombreUsuarioDestino,
                                   @RequestParam("contenido") String contenido) {

        UsuarioModel usuarioActivo = usuarioRepo.findByNombre(request.getUserPrincipal().getName()).orElseThrow();
        UsuarioModel usuarioDestinatario = usuarioRepo.findByNombre(nombreUsuarioDestino).orElseThrow();

        // Se delega al servicio interno el envío del correo
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parametros = new LinkedMultiValueMap<>();
        parametros.add("contenido", contenido);
        parametros.add("correo_destino", usuarioDestinatario.getCorreo());
        parametros.add("nombre_remitente", usuarioActivo.getNombre());

        HttpEntity<MultiValueMap<String, String>> requestServicio = new HttpEntity<>(parametros, headers);

        ResponseEntity<String> result = restTemplate.postForEntity("http://serv1:3000/correo", requestServicio, String.class);

        System.out.println(result);


        return "redirect:/buscador-ofertas";
    }
}
