package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
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

    public NetworkOmcpDao(SensorNetConfig sensorNetConfig) {
        this.sensorNetConfig = sensorNetConfig;
    }

    @Override
    public NetworkCoTo getById(String id) {
        String ip = this.sensorNetConfig.getIp();
        String username = this.sensorNetConfig.getUsername();
        String password = this.sensorNetConfig.getPassword();
        String uri = String.format(this.sensorNetConfig.getNetworkIdUri(), id);
        OmcpClient omcpClient = new RabbitClient(ip, username, password);
        Response response = omcpClient.doGet(uri);
        NetworkCoTo networkCoto = response.getContent(NetworkCoTo.class);
        return networkCoto;
    }

    @Override
    public List<NetworkCoTo> getAll() {
        String ip = this.sensorNetConfig.getIp();
        String username = this.sensorNetConfig.getUsername();
        String password = this.sensorNetConfig.getPassword();
        String uri = this.sensorNetConfig.getNetworksUri();
        OmcpClient omcpClient = new RabbitClient(ip, username, password);
        Response response = omcpClient.doGet(uri);
        NetworkCoTo[] networkCotoArray = response.getContent(NetworkCoTo[].class);
        List<NetworkCoTo> networkCotoList = Arrays.asList(networkCotoArray);
        return networkCotoList;
    }
}
