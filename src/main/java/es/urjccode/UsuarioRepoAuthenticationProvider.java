package es.urjccode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import es.urjccode.models.UsuarioModel;
import es.urjccode.repositories.UsuarioRepo;

//@Component
/*public class UsuarioRepoAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private Usuario usuarioSession;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("1");
		UsuarioModel usuario = usuarioRepo.findByNombre(authentication.getName());
		
		if (usuario == null) {
			throw new BadCredentialsException("Usuario no encontrado");
		}
		
		System.out.println(usuario.getNombre() + " " + usuario.getContrasenya());
		
		String contrasenya = (String) authentication.getCredentials();
		
		System.out.println(contrasenya);
		
		if (!contrasenya.equals(usuario.getContrasenya())) {
			throw new BadCredentialsException("COntraseña incorrecta");
		}
		
		usuarioSession.setId(usuario.getId());
		
		return new UsernamePasswordAuthenticationToken(usuario.getNombre(), contrasenya);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}*/
