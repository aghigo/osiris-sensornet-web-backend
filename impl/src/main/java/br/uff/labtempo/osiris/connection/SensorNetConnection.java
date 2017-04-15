package br.uff.labtempo.osiris.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.exceptions.client.ConnectionException;
import br.uff.labtempo.osiris.model.HealthDependency;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class responsible for the connection to the SensorNet module
 * values are retrieved from the default application.properties configuration file
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
@Getter
@PropertySource(value = "classpath:application.properties")
public class SensorNetConnection {
    @Value("${sensornet.ip:127.0.0.1}")
    private String ip;

    @Value("${sensornet.port:8090}")
    private int port;

    @Value("${sensornet.username:guest}")
    private String username;

    @Value("${sensornet.password:guest}")
    private String password;

    /**
     * Connects to the SensorNet module and return a OMCP connection
     * @see OmcpClient
     * @see RabbitClient
     * @return OmcpClient (OMCP connection)
     * @throws ConnectionException
     */
    public OmcpClient getConnection() throws ConnectionException {
        return new RabbitClient(ip, username, password);
    }
}
