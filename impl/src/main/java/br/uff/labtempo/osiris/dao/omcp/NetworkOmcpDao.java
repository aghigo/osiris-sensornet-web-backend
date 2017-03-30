package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
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
    public NetworkSnTo getById(String id) {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdUri(), id);
        Response response = omcpClient.doGet(uri);
        NetworkSnTo networkSnTo = response.getContent(NetworkSnTo.class);
        return networkSnTo;
    }

    @Override
    public List<NetworkSnTo> getAll() {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = this.sensorNetConfig.getNetworksUri();
        Response response = omcpClient.doGet(uri);
        NetworkSnTo[] networkSnToArray = response.getContent(NetworkSnTo[].class);
        List<NetworkSnTo> networkSnToList = Arrays.asList(networkSnToArray);
        return networkSnToList;
    }
}
