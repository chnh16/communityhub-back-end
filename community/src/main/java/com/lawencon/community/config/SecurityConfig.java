package com.lawencon.community.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lawencon.community.filter.AuthorizationFilter;
import com.lawencon.community.service.UserService;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterchain(final HttpSecurity http, final AuthorizationFilter authorizationFilter)
			throws Exception {
		http.cors();
		http.csrf().disable();
		http.addFilterAt(authorizationFilter, BasicAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(final HttpSecurity http, final UserService userService,
			final BCryptPasswordEncoder byCryptPasswordEncoder) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userService)
				.passwordEncoder(byCryptPasswordEncoder).and().build();
	}

	@Bean
	public List<RequestMatcher> matchers() {
		final List<RequestMatcher> matchers = new ArrayList<>();
		matchers.add(new AntPathRequestMatcher("/users/login", HttpMethod.POST.name()));
		matchers.add(new AntPathRequestMatcher("/users/regis", HttpMethod.POST.name()));
		matchers.add(new AntPathRequestMatcher("/users/verify", HttpMethod.PUT.name()));
		matchers.add(new AntPathRequestMatcher("/users/verify/generate", HttpMethod.POST.name()));
		matchers.add(new AntPathRequestMatcher("/files/**", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/industry", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/positions", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/report/**", HttpMethod.GET.name()));
		return matchers;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> matchers().forEach(r -> {
			web.ignoring().requestMatchers(r);
		});
	}
	
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
	
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.PATCH.name());
			}
		};
	}

}
