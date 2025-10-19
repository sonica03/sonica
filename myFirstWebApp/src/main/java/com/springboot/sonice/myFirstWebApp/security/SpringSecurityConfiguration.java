package com.springboot.sonice.myFirstWebApp.security;

import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//This class is the configuration source for Spring
@Configuration
public class SpringSecurityConfiguration {

	// To store user name and password we make use of LDAP or Database
	@Bean
	public InMemoryUserDetailsManager createCredentialManager() {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
		UserDetails user1 = createNewUser(passwordEncoder, "sonica", "dummy");
		UserDetails user2 = createNewUser(passwordEncoder, "kalpana", "lovely");
		// This method accepts variable arguments can pass as many as i want
		return new InMemoryUserDetailsManager(user1, user2);
	}

	private UserDetails createNewUser(Function<String, String> passwordEncoder, String userName, String password) {
		UserDetails user = User.builder().passwordEncoder(passwordEncoder).username(userName).password(password)
				.roles("USER", "ADMIN").build();
		return user;
	}

	// This is custom encoder instead of using deprecated method inside User
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		// This is the core authorization rule: it mandates that all incoming HTTP
		// requests (anyRequest()) must be authenticated (the user must be logged in)
		httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		// Spring Security will redirect them to a default login page.
		httpSecurity.formLogin(withDefaults());

		// We are authenticating all requests, if unauthorized we can show form login
		// Cross site request forge
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
		return httpSecurity.build();
	}
}
