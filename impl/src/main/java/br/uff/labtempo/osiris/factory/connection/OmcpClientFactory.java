package br.uff.labtempo.osiris.factory.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Instantiate an OmcpClient
 * in a manner that can be pooled
 * Created by osiris on 15/06/17.
 */
public class OmcpClientFactory extends BasePooledObjectFactory<OmcpClient> {

    private String host;
    private String username;
    private String password;

    public OmcpClientFactory(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    @Override
    public OmcpClient create() throws Exception {
        return new RabbitClient(host, username, password);
    }

    @Override
    public PooledObject<OmcpClient> wrap(OmcpClient omcpClient) {
        return new DefaultPooledObject<OmcpClient>(omcpClient);
    }
}
