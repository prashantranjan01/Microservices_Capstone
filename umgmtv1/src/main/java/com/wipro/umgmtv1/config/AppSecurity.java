package com.wipro.umgmtv1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

 

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) 
public class AppSecurity {
	
	 
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

	    	http.csrf(csrf -> csrf.disable())
	        .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/user/login/**","/user/register/**").permitAll()
	            .anyRequest().authenticated()
	        );
	    return http.build();
	    }

	
}
