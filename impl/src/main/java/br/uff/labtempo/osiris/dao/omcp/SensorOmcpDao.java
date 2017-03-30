package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("sensorOmcpDao")
public class SensorOmcpDao implements SensorRepository {

    @Autowired
    private SensorNetConfig sensorNetConfig;

    @Autowired
    private SensorNetConnection connection;

    @Override
    public SensorSnTo getByCollectorIdAndNetworkId(String networkId, String collectorId, String sensorId) {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdCollectorIdSensorIdUri(), networkId, collectorId, sensorId);
        Response response = omcpClient.doGet(uri);
        SensorSnTo sensorSnTo = response.getContent(SensorSnTo.class);
        return sensorSnTo;
    }

    @Override
    public List<SensorSnTo> getAllByCollectorIdAndNetworkId(String networkId, String collectorId) {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdCollectorIdSensorsUri(), networkId, collectorId);
        Response response = omcpClient.doGet(uri);
        SensorSnTo[] sensorSnToArray = response.getContent(SensorSnTo[].class);
        List<SensorSnTo> sensorSnToList = Arrays.asList(sensorSnToArray);
        return sensorSnToList;
    }

    @Override
    public List<SensorSnTo> getAllByNetworkId(String networkId) {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdSensorsUri(), networkId);
        Response response = omcpClient.doGet(uri);
        SensorSnTo[] sensorSnToArray = response.getContent(SensorSnTo[].class);
        List<SensorSnTo> sensorSnToList = Arrays.asList(sensorSnToArray);
        return sensorSnToList;
    }
}