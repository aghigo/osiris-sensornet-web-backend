package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("networkOmcpDao")
public class NetworkOmcpDao implements NetworkRepository {

    @Autowired
    private SensorNetConfig sensorNetConfig;

    @Autowired
    private SensorNetConnection connection;

    @Override
    public NetworkCoTo getById(String id) {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdUri(), id);
        Response response = omcpClient.doGet(uri);
        NetworkCoTo networkCoto = response.getContent(NetworkCoTo.class);
        return networkCoto;
    }

    @Override
    public List<NetworkCoTo> getAll() {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = this.sensorNetConfig.getNetworksUri();
        Response response = omcpClient.doGet(uri);
        NetworkCoTo[] networkCotoArray = response.getContent(NetworkCoTo[].class);
        List<NetworkCoTo> networkCotoList = Arrays.asList(networkCotoArray);
        return networkCotoList;
    }
}
