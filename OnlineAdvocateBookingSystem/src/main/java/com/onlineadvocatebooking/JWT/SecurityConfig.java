package com.onlineadvocatebooking.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.SecurityBuilder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurationAdapter{
		// TODO Auto-generated constructor stub
	@Autowired
   CustomerUserDetailsService  customerUserDetailsService;
	@Autowired
	JwtFilter jwtFilter;
	
	protected void configure(AuthenticationManagerBuilder auth )throws Exception{
		auth.userDetailsService(customerUserDetailsService);
		
	}
//Here i create a bean of password in coder
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public PasswordEncoder passwordEncoder() {
	 	return NoOpPasswordEncoder.getInstance();
	}
	public AuthenticationManager authenticationManagerBean(){
		return super.authenticationManagerBean();
	}

protected void configure(HttpSecurity http) throws Exception {
http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and()
.csrf().disable()
.authorizeHttpRequests().requestMatchers("/user/login", "/user/signup", "user/forgotPassword").permitAll()
.anyRequest().authenticated()
.and().exceptionHandling()
.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//add the filter
http.addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);

}

}

