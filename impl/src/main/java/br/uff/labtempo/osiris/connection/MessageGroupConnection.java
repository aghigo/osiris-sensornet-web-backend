package br.uff.labtempo.osiris.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.osiris.configuration.OsirisMessageGroupConfig;
import org.springframework.stereotype.Component;

/**
 * OMCP Connection to a message group (e.g. collector, update, notification)
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@Component
public class MessageGroupConnection {

    /**
     * Get a OMCP connection to a MessageGroup
     * @param osirisMessageGroupConfig
     * @return Connection to the MessageGroup
     */
    public OmcpClient getConnection(OsirisMessageGroupConfig osirisMessageGroupConfig) {
        if(osirisMessageGroupConfig != null) {
            String ip = osirisMessageGroupConfig.getIp();
            int port = osirisMessageGroupConfig.getPort();
            String username = osirisMessageGroupConfig.getUsername();
            String password = osirisMessageGroupConfig.getPassword();
            return new RabbitClient(ip, username, password);
        }
        throw new RuntimeException("MessageGroupConnection error: Invalid MessageGroup configuration.");
    }
}
