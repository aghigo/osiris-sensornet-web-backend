package br.uff.labtempo.osiris.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.exceptions.client.ConnectionException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Class responsible for the connection to the Function module
 * Default configuration is the same as VirtualSensorNet
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
@Getter
@PropertySource(value = "classpath:application.properties")
public class FunctionConnection {
    @Value("${sensornet.ip:127.0.0.1}")
    private String ip;

    @Value("${sensornet.port:8091}")
    private int port;

    @Value("${sensornet.username:guest}")
    private String username;

    @Value("${sensornet.password:guest}")
    private String password;

    /**
     * Connects to the Function module using the ip, port, username and password
     * from the application.properties configuration file.
     * @see OmcpClient
     * @see RabbitClient
     * @return OmcpClient connection
     * @throws ConnectionException
     */
    public OmcpClient getConnection() throws ConnectionException {
        return new RabbitClient(ip, username, password);
    }
}
