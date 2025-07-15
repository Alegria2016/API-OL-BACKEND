package com.backend.config;

import com.backend.enums.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilter {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authConfig -> {
                    authConfig.requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll();

                    authConfig.requestMatchers(HttpMethod.GET, "/api/v1/reports/active-merchants").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/api/v1/merchants").permitAll();

                    authConfig.requestMatchers(AUTH_WHITELIST).permitAll();

                    authConfig.requestMatchers("/error").permitAll();

                    authConfig.requestMatchers(HttpMethod.GET, "/api/v1/auth/profile").hasAuthority(Permission.GET_PROFILE_USER.name());

                    authConfig.requestMatchers(HttpMethod.GET, "/api/v1/users/{id}").hasAuthority(Permission.READ_USER_BY_ID.name());
                    authConfig.requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority(Permission.READ_ALL_USERS.name());
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}").hasAuthority(Permission.UPDATE_USER.name());
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/v1/users/{id}").hasAuthority(Permission.DELETE_USER.name());


                    //authConfig.requestMatchers(HttpMethod.GET, "/api/v1/products/{id}").hasAuthority(Permission.READ_PRODUCT_BY_ID.name());
                    //authConfig.requestMatchers(HttpMethod.GET, "/api/v1/products").hasAuthority(Permission.READ_ALL_PRODUCTS.name());
                    authConfig.requestMatchers(HttpMethod.POST, "/api/v1/products").hasAuthority(Permission.SAVE_ONE_PRODUCT.name());
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/v1/products/{id}").hasAuthority(Permission.UPDATE_PRODUCT.name());
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/v1/products/{id}").hasAuthority(Permission.DELETE_PRODUCT.name());
                    authConfig.requestMatchers(HttpMethod.GET, "/api/v1/products/user/{id}").hasAuthority(Permission.GET_PRODUCTOS_BY_USER.name());

                    authConfig.anyRequest().denyAll();


                });

        return http.build();

    }
}
