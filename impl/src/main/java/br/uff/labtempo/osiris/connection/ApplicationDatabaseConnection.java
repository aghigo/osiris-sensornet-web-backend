package br.uff.labtempo.osiris.connection;

import br.uff.labtempo.osiris.configuration.ApplicationDatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

/**
 * Connection to the application main relational database
 * @author andre.ghigo
 * @version 1.0
 * @since  29/04/17.
 */
@Component("applicationDatabaseConnection")
public class ApplicationDatabaseConnection {

    @Autowired
    private ApplicationDatabaseConfig applicationDatabaseConfig;

    public JdbcTemplate getJdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.applicationDatabaseConfig.getDriverClassName());
        dataSource.setUrl(this.applicationDatabaseConfig.getDataSourceUrl());
        dataSource.setUsername(this.applicationDatabaseConfig.getUsername());
        dataSource.setPassword(this.applicationDatabaseConfig.getPassword());
        return new JdbcTemplate(dataSource);
    }

}
