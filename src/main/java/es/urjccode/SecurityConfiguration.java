package es.urjccode;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.urjccode.repositories.UserDetailsServiceRepo;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceRepo userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//public pages
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/verificar").permitAll();
		http.authorizeRequests().antMatchers("/error").permitAll();
		http.authorizeRequests().antMatchers("/error_inf").permitAll();
		http.authorizeRequests().antMatchers("/crear-usuario").permitAll();
		http.authorizeRequests().antMatchers("/login-error").permitAll();
		http.authorizeRequests().antMatchers("/crear-usuario").permitAll();
		http.authorizeRequests().antMatchers("/buscador-ofertas").permitAll();
		http.authorizeRequests().antMatchers("/detalles-oferta/{id_oferta}").permitAll();
		http.authorizeRequests().antMatchers("/usuario-creado").permitAll();
		
		http.authorizeRequests().antMatchers("/favicon.ico").permitAll();
		
		//private pages
		http.authorizeRequests().anyRequest().hasAnyRole("USER");
		
		//login
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("input_usuario");
		http.formLogin().passwordParameter("input_contrasenya");
		http.formLogin().defaultSuccessUrl("/");
		http.formLogin().failureUrl("/login-error");
		
		//logout
		http.logout().logoutUrl("/logout"); //la asociada al botón
		http.logout().logoutSuccessUrl("/logout-done"); //a donde te redirige
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
	}
/*nota:
 * logger get:
 * <input type="hidden" name="_csrf" value="{{token}}"/>
 * logger post
 * <input type="hidden" name="_csrf" value="{{token}}"/>
 * login-error get
 * <input type="hidden" name="_csrf" value="{{token}}"/>
 * buscador-ofertas
 * <input type="hidden" name="_csrf" value="{{token}}"/>*/

}
