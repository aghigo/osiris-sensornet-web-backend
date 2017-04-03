package br.uff.labtempo.osiris.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:application.properties")
public class VirtualSensorNetConnection {
    @Value("${virtualsensornet.ip:127.0.0.1}")
    private String ip;

    @Value("${virtualsensornet.port:8091}")
    private int port;

    @Value("${virtualsensornet.username:guest}")
    private String username;

    @Value("${virtualsensornet.password:guest}")
    private String password;

    public OmcpClient getConnection() {
        return new RabbitClient(ip, username, password);
    }
}
