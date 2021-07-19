package com.dignity.puppymarket.config;

import com.dignity.puppymarket.filters.AuthenticationErrorFilter;
import com.dignity.puppymarket.filters.JwtAuthenticationFilter;
import com.dignity.puppymarket.service.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.Filter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationService authenticationService;

    public SecurityJavaConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter authenticationFilter = new JwtAuthenticationFilter(
                authenticationManager(), authenticationService);
        Filter authenticationErrorFilter = new AuthenticationErrorFilter();

//        http
//                .csrf().disable()
//                .headers()
//                .frameOptions().disable()
//                .and()
//                .addFilter(authenticationFilter)
//                .addFilterBefore(authenticationErrorFilter, JwtAuthenticationFilter.class)
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        http
                .authorizeRequests()
                .anyRequest()
                .permitAll();

        http
                .formLogin()
                .loginProcessingUrl("/")
                .defaultSuccessUrl("/")
                .permitAll();

        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        http
                .csrf().disable()
                .headers()
                .frameOptions().disable();
    }
}
