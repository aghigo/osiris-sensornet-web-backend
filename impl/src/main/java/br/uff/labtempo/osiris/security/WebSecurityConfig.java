package br.uff.labtempo.osiris.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Class to initialize spring-security configurations
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/health", "/h2-console/*", "/resource-status").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().and().csrf().disable();
    }
}
