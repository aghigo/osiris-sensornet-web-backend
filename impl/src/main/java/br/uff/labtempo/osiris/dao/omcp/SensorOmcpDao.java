package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
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

    @Autowired
    private SensorNetConnection connection;

    @Override
    public SensorCoTo getByCollectorIdAndNetworkId(String networkId, String collectorId, String sensorId) {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdCollectorIdSensorIdUri(), networkId, collectorId, sensorId);
        Response response = omcpClient.doGet(uri);
        SensorCoTo sensorCoTo = response.getContent(SensorCoTo.class);
        return sensorCoTo;
    }

    @Override
    public List<SensorCoTo> getAllByCollectorIdAndNetworkId(String networkId, String collectorId) {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdCollectorIdSensorsUri(), networkId, collectorId);
        Response response = omcpClient.doGet(uri);
        SensorCoTo[] sensorCoToArray = response.getContent(SensorCoTo[].class);
        List<SensorCoTo> sensorCoToList = Arrays.asList(sensorCoToArray);
        return sensorCoToList;
    }

    @Override
    public List<SensorCoTo> getAllByNetworkId(String networkId) {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdSensorsUri(), networkId);
        Response response = omcpClient.doGet(uri);
        SensorCoTo[] sensorCoToArray = response.getContent(SensorCoTo[].class);
        List<SensorCoTo> sensorCoToList = Arrays.asList(sensorCoToArray);
        return sensorCoToList;
    }
}