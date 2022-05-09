package com.sgrmr.app.util.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails usuarioEmpleado = User
				.withUsername("empleado")
				.password("$2a$10$8DfCzDohmV.ZmFyZGN5cyOm2W6URo5KPCIQPltoBkZnDuGtyp8AhO")
				.roles("USER")
				.build();
		
		UserDetails usuarioAdmin = User
				.withUsername("admin")
				.password("$2a$10$obTvBmYwVw0mKsy4SlKuR.5RG9gSksRBmCC428yo7RCECYeQ.DIZS")
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(usuarioEmpleado, usuarioAdmin);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/index").permitAll()
			.antMatchers("/formulario_empleado/*","/eliminar/*").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/index")
				.permitAll()
			.and()
			.logout().permitAll();
	}
}
