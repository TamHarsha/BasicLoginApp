package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/welcome","/register", "/login","/h2-console/**").permitAll() // Allow access to welcome page for everyone
                .anyRequest().authenticated() // Require authentication for URLs starting with /user/
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .usernameParameter("userName")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/user/list",true)// Specify the login page URL
                .permitAll() // Allow everyone to access the login page
            )
            .logout((logout) -> logout
            		.logoutUrl("/logout")
                .permitAll() // Allow everyone to access the logout functionality
            );
		
		 http.csrf().disable(); // Necessary for H2 console
		    http.headers().frameOptions().disable(); // Necessary for H2 console
		
		return http.build();
		
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
//	@Bean
//    public UserDetailsService userDetailsService() {
//        return userService; 
//    }


}
