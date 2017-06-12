package br.uff.labtempo.osiris.factory.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.exceptions.client.ConnectionException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Class responsible for the connection to the VirtualSensorNet module
 * values are retrieved from the default application.properties configuration file
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
@Getter
@PropertySource(value = "classpath:application.properties")
public class VirtualSensorNetConnectionFactory {
    @Value("${virtualsensornet.ip:127.0.0.1}")
    private String ip;

    @Value("${virtualsensornet.port:8091}")
    private int port;

    @Value("${virtualsensornet.username:guest}")
    private String username;

    @Value("${virtualsensornet.password:guest}")
    private String password;

    /**
     * Connects to the VirtualSensorNet module and return a OMCP connection
     * @see OmcpClient (OMCP connection interface)
     * @see RabbitClient (OMCP connection implementation)
     * @return OmcpClient (OMCP connection interface)
     * @throws ConnectionException
     */
    public OmcpClient getConnection() throws ConnectionException {
        return new RabbitClient(ip, username, password);
    }
}
