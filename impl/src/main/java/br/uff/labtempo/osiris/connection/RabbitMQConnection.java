package br.uff.labtempo.osiris.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.exceptions.client.ConnectionException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Class responsible for the connection to the RabbitMQ queue
 * values are retrieved from the default application.properties configuration file
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
@Getter
@PropertySource(value = "classpath:application.properties")
public class RabbitMQConnection {
    @Value("${rabbitmq.moduleName:rabbitmq}")
    private String moduleName;

    @Value("${rabbitmq.ip:127.0.0.1}")
    private String ip;

    @Value("${rabbitmq.port:5672}")
    private int port;

    @Value("http://${rabbitmq.ip:127.0.0.1}:${rabbitmq.port:5672}/")
    private String moduleUri;

    @Value("${rabbitmq.username:guest}")
    private String username;

    @Value("${rabbitmq.password:guest}")
    private String password;

    /**
     * Get a OMCP connection to the RabbitMQ
     * @see OmcpClient
     * @see RabbitClient
     * @return OmcpClient connection
     * @throws ConnectionException
     */
    public OmcpClient getConnection() throws ConnectionException {
        return new RabbitClient(ip, username, password);
    }
}
