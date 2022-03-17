package es.urjccode.security;

import es.urjccode.models.UsuarioModel;
import es.urjccode.repositories.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepoUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepo usuRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioModel usuario = usuRepo.findByNombre(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

        for (String role : usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        //sesionUsuario.setId(usuario.getId());

        return new org.springframework.security.core.userdetails.User(
				usuario.getNombre(),
                usuario.getContrasenya(),
                roles);



    }

}
