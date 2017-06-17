package br.uff.labtempo.osiris.factory.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.exceptions.client.ConnectionException;
import lombok.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Class responsible for the connection to the RabbitMQ queue
 * values are retrieved from the default application.properties configuration file
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@PropertySource(value = "classpath:application.properties")
public class OsirisConnectionFactory {
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

    private static ObjectPool<OmcpClient> pool;

    @PostConstruct
    public void createPool() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(100);
        genericObjectPoolConfig.setMaxIdle(1);
        genericObjectPoolConfig.setMinIdle(100);
        //genericObjectPoolConfig.setTestOnBorrow(true);
        genericObjectPoolConfig.setMaxWaitMillis(TimeUnit.SECONDS.toMillis(10L));
        //genericObjectPoolConfig.setFairness(true);
        this.pool = new GenericObjectPool<>(new OmcpClientFactory(ip, username, password), genericObjectPoolConfig);
    }

    /**
     * Get a OMCP connection to the RabbitMQ
     * Borrow a OmcpClient from the Connection Pool
     * @see OmcpClient
     * @see RabbitClient
     * @return OmcpClient connection
     * @throws ConnectionException
     */
    public synchronized OmcpClient getConnection() throws ConnectionException {
        try {
            OmcpClient omcpClient = this.pool.borrowObject();
            return omcpClient;
        } catch (Exception e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    /**
     * Close an OMCP connection from RabbitMQ
     * Return the OmcpClient to the Connection Pool
     * @param omcpClient
     * @throws Exception
     */
    public synchronized void closeConnection(OmcpClient omcpClient) throws ConnectionException {
        try {
            this.pool.returnObject(omcpClient);
        } catch (Exception e) {
            throw new ConnectionException(e.getMessage());
        }
    }
}
