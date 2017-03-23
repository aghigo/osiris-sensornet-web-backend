package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("sensorOmcpDao")
public class SensorOmcpDao implements SensorRepository {

    @Autowired
    private SensorNetConfig sensorNetConfig;

    @Override
    public SensorCoTo getByCollectorIdAndNetworkId(String networkId, String collectorId, String sensorId) {
        String ip = this.sensorNetConfig.getIp();
        String username = this.sensorNetConfig.getUsername();
        String password = this.sensorNetConfig.getPassword();
        String uri = String.format(this.sensorNetConfig.getNetworkIdCollectorIdSensorIdUri(), networkId, collectorId, sensorId);
        OmcpClient omcpClient = new RabbitClient(ip, username, password);
        Response response = omcpClient.doGet(uri);
        SensorCoTo sensorCoTo = response.getContent(SensorCoTo.class);
        return sensorCoTo;
    }

    @Override
    public List<SensorCoTo> getAllByCollectorIdAndNetworkId(String networkId, String collectorId) {
        String ip = this.sensorNetConfig.getIp();
        String username = this.sensorNetConfig.getUsername();
        String password = this.sensorNetConfig.getPassword();
        String uri = String.format(this.sensorNetConfig.getNetworkIdCollectorIdSensorsUri(), networkId, collectorId);
        OmcpClient omcpClient = new RabbitClient(ip, username, password);
        Response response = omcpClient.doGet(uri);
        SensorCoTo[] sensorCoToArray = response.getContent(SensorCoTo[].class);
        List<SensorCoTo> sensorCoToList = Arrays.asList(sensorCoToArray);
        return sensorCoToList;
    }

    @Override
    public List<SensorCoTo> getAllByNetworkId(String networkId) {
        String ip = this.sensorNetConfig.getIp();
        String username = this.sensorNetConfig.getUsername();
        String password = this.sensorNetConfig.getPassword();
        String uri = String.format(this.sensorNetConfig.getNetworkIdSensorsUri(), networkId);
        OmcpClient omcpClient = new RabbitClient(ip, username, password);
        Response response = omcpClient.doGet(uri);
        SensorCoTo[] sensorCoToArray = response.getContent(SensorCoTo[].class);
        List<SensorCoTo> sensorCoToList = Arrays.asList(sensorCoToArray);
        return sensorCoToList;
    }
}
