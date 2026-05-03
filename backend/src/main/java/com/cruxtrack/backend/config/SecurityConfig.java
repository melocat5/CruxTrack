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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/auth/admin-reset-password").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()
				.requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated()
				.requestMatchers(HttpMethod.GET, "/api/users/me").authenticated()
				.requestMatchers(HttpMethod.PUT, "/api/users/me").authenticated()
				.requestMatchers(HttpMethod.POST, "/api/users/me/change-password").authenticated()
				.requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/users/*/deactivate").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/api/morning-checks").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/morning-checks").authenticated()
				.requestMatchers(HttpMethod.GET, "/api/morning-tasks").authenticated()
				.requestMatchers(HttpMethod.POST, "/api/morning-tasks").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/morning-tasks/**").hasRole("ADMIN")
				.requestMatchers("/api/**").authenticated()
				.anyRequest().denyAll())
			.exceptionHandling(ex -> ex.authenticationEntryPoint(
				new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
			.httpBasic(basic -> basic.disable())
			.formLogin(form -> form.disable());
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setExposedHeaders(List.of("Set-Cookie"));
		config.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
