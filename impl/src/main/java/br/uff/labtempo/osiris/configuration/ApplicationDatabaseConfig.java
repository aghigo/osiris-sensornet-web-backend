package br.uff.labtempo.osiris.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * Class to map the osiris-web application database configurations
 * can be changed in the application.properties file
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@PropertySource(value = "classpath:application.properties")
public class ApplicationDatabaseConfig {

    @Value("${spring.datasource.url:jdbc:h2:mem:testdb}")
    private String dataSourceUrl;

    @Value("${spring.datasource.driver-class-name:org.h2.Driver}")
    private String driverClassName;

    @Value("${spring.datasource.username:sa}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;
}
