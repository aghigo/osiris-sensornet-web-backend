package br.uff.labtempo.osiris.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OmcpConnection {

    private String ip = "127.0.0.1";
    private int port = 8090;
    private String username = "guest";
    private String password = "guest";

    public OmcpClient getConnection() {
        return new RabbitClient(ip, username, password);
    }

}
