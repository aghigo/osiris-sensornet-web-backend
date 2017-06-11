package br.uff.labtempo.osiris.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.exceptions.client.ConnectionException;
import br.uff.labtempo.osiris.configuration.FunctionModuleConfig;
import lombok.Getter;
import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Class responsible for the connection to the FunctionFactory module
 * Default configuration is the same as VirtualSensorNet
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
@Getter
@PropertySource(value = "classpath:application.properties")
public class FunctionConnection {

    /**
     * Connects to the FunctionFactory module using the ip, port, username and password
     * @see OmcpClient
     * @see RabbitClient
     * @see FunctionModuleConfig
     * @param functionModuleConfig
     * @return OmcpClient connection
     * @throws ConnectionException
     */
    public OmcpClient getConnection(FunctionModuleConfig functionModuleConfig) throws ConnectionException {
        if(functionModuleConfig != null) {
            String ip = functionModuleConfig.getIp();
            String username = functionModuleConfig.getUsername();
            String password = functionModuleConfig.getPassword();
            return new RabbitClient(ip, username, password);
        }
        throw new RuntimeException("FunctionConnection error: Invalid function configuration.");
    }
}
