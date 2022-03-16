package es.urjccode.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.urjccode.SesionUsuario;
import es.urjccode.models.UsuarioModel;

@Service
public class UserDetailsServiceRepo implements UserDetailsService{
	
	@Autowired
	private UsuarioRepo usuRepo;
	
	@Autowired
	private SesionUsuario sesionUsuario;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UsuarioModel usuario = usuRepo.findByNombre(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		List<GrantedAuthority> roles = new ArrayList();
		
		for(String role : usuario.getRoles()) {
			roles.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		
		sesionUsuario.setId(usuario.getId());
		
		return new org.springframework.security.core.userdetails.User(usuario.getNombre(), usuario.getContrasenya(), roles);
		
	}

}
