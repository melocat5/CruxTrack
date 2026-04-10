package com.cruxtrack.backend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// CRUXTRACK: CENTRAL PLACE THAT CONFIGURES PASSWORDS, LOGIN BEHAVIOR, SESSIONS, CSRF, CORS, AND URL RULES
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	// CRUXTRACK: ONE-WAY ENCRYPTION FOR PASSWORDS — SAME PLAIN TEXT CAN LOOK DIFFERENT EACH SAVE (SALT)
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	// CRUXTRACK: USED BY AUTHCONTROLLER TO RUN USERNAME/PASSWORD LOGIN
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	// CRUXTRACK: ORDER MATTERS: FIRST MATCH WINS — AUTH PATHS ARE OPEN; ADMIN PATH NEEDS ROLE; REST NEEDS LOGIN
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// CRUXTRACK: CORS = BROWSER RULES FOR CALLING THIS API FROM A DIFFERENT PORT (E.G. ANGULAR ON 4200)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			// CRUXTRACK: CSRF PROTECTION FOR BROWSER APPS — COOKIE + HEADER MUST MATCH ON POST/PUT/DELETE
			.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			// CRUXTRACK: CREATE A SESSION WHEN NEEDED (LOGIN) — THAT IS HOW SESSION COOKIES WORK
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.GET, "/api/auth/me").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/logout").permitAll()
				// CRUXTRACK: ONLY ACCOUNTS WITH ROLE "ADMIN" (SPRING STORES AS ROLE_ADMIN)
				.requestMatchers("/api/admin/**").hasRole("ADMIN")
				.requestMatchers("/api/**").authenticated()
				.anyRequest().denyAll())
			// CRUXTRACK: IF NOT LOGGED IN, RETURN HTTP 401 INSTEAD OF REDIRECTING TO A LOGIN PAGE
			.exceptionHandling(ex -> ex.authenticationEntryPoint(
				new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
			.httpBasic(basic -> basic.disable())
			.formLogin(form -> form.disable());
		return http.build();
	}

	@Bean
	// CRUXTRACK: ALLOWS THE ANGULAR DEV SERVER ORIGIN TO SEND COOKIES AND HEADERS TO THIS API
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setExposedHeaders(List.of("Set-Cookie"));
		config.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
