package com.vehicule.flotte_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.vehicule.flotte_management.security.JWTAuthorizationFilter;

@SpringBootApplication
public class FlotteManagementApplication {
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/users/authenticate").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/users/register").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/encheres").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/encheres").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/encheres/{enchereid}").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/encheres/search").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/encheres/{userid}/history").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/encheres/{userid}/expired").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/enchere/admin").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/enchere/newcategorie").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/enchere/listecategorie").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/enchere/supprimer/{id}").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/enchere/commission/{pourcentage}").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/enchere/recharge/{id}").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/enchere/refuser/{id}").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/enchere/archive").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/enchere/liste_demande").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/enchere/listestat").permitAll()
				.anyRequest().authenticated();
			http.cors();
		}	
	}

	@Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                WebMvcConfigurer.super.addCorsMappings(registry);
                registry.addMapping("/**").allowedMethods("POST", "PUT", "DELETE", "OPTIONS").allowedOrigins("*").allowedHeaders("*").maxAge(-1).allowCredentials(false);
            }
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(FlotteManagementApplication.class, args);
	}
}
