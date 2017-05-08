package br.uff.labtempo.osiris.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.osiris.configuration.MessageGroupConfig;
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
     * @param messageGroupConfig
     * @return Connection to the MessageGroup
     */
    public OmcpClient getConnection(MessageGroupConfig messageGroupConfig) {
        if(messageGroupConfig != null) {
            String ip = messageGroupConfig.getIp();
            int port = messageGroupConfig.getPort();
            String username = messageGroupConfig.getUsername();
            String password = messageGroupConfig.getPassword();
            return new RabbitClient(ip, username, password);
        }
        throw new RuntimeException("MessageGroupConnection error: Invalid MessageGroup configuration.");
    }
}
