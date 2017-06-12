package br.uff.labtempo.osiris.factory.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.exceptions.client.ConnectionException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Class responsible for the connection to the FunctionModuleFactory module
 * Default configuration is the same as VirtualSensorNet
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
@Getter
@PropertySource(value = "classpath:application.properties")
public class FunctionConnectionFactory {

    @Autowired
    private CommunicationLayerConnectionFactory communicationLayerConnectionFactory;

    /**
     * Connects to the Function module
     *
     * @return OmcpClient connection
     * @throws ConnectionException
     */
    public OmcpClient getConnection() throws ConnectionException {
        String host = this.communicationLayerConnectionFactory.getIp();
        String username = this.communicationLayerConnectionFactory.getUsername();
        String password = this.communicationLayerConnectionFactory.getPassword();
        return new RabbitClient(host, username, password);
    }
}
