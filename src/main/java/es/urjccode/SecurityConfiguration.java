package es.urjccode;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//public pages
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/logout").permitAll();
		http.authorizeRequests().antMatchers("/error").permitAll();
		http.authorizeRequests().antMatchers("/login-error").permitAll();
		http.authorizeRequests().antMatchers("/crear-usuario").permitAll();
		http.authorizeRequests().antMatchers("/buscador-ofertas").permitAll();
		http.authorizeRequests().antMatchers("/detalles-oferta/{id_oferta}").permitAll();
		
		
		//private pages
		http.authorizeRequests().anyRequest().authenticated();
		
		//login
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("input_usuario");
		http.formLogin().passwordParameter("input_password");
		http.formLogin().defaultSuccessUrl("/");
		http.formLogin().failureUrl("/loginError");
		
		//logout
		http.logout().logoutUrl("/logout"); //la asociada al botón
		http.logout().logoutSuccessUrl("/logout-done"); //a donde te redirige
		
		//CSRF
		//http.csrf().disable();
		
	}

}
