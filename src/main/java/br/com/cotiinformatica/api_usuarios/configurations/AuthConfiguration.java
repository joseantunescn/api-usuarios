package br.com.cotiinformatica.api_usuarios.configurations;

import br.com.cotiinformatica.api_usuarios.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfiguration {

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    FilterRegistrationBean<AuthenticationFilter>     authenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthenticationFilter(secretKey));
        registration.addUrlPatterns("/api/usuario/obter-dados");
        return registration;
    }

}
