package br.uff.labtempo.osiris.security;

import br.uff.labtempo.osiris.configuration.ApplicationDatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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

    @Value("${spring.datasource.url:jdbc:h2:mem:testdb}")
    private String dbUrl;

    @Value("${spring.datasource.username:sa}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name:org.h2.Driver}")
    private String dbDriverClassName;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("guest").password("guest").roles("USER");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.dbDriverClassName);
        dataSource.setUrl(this.dbUrl);
        dataSource.setUsername(this.dbUsername);
        dataSource.setPassword(this.dbPassword);

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password, enabled from USER_ACCOUNT where username=?")
                .authoritiesByUsernameQuery("select username, role from AUTHORITIES where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().and().csrf().disable();

        http.headers().frameOptions().disable();
    }
}