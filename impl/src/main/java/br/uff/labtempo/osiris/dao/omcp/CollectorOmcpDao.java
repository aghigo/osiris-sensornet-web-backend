package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.repository.CollectorRepository;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("collectorOmcpDao")
public class CollectorOmcpDao implements CollectorRepository {

    @Autowired
    private SensorNetConfig sensorNetConfig;

    @Autowired
    private SensorNetConnection connection;

    @Override
    public CollectorCoTo getByNetworkId(String networkId, String collectorId) {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdCollectorIdUri(), networkId, collectorId);
        Response response = omcpClient.doGet(uri);
        CollectorCoTo collectorCoTo = response.getContent(CollectorCoTo.class);
        return collectorCoTo;
    }

    @Override
    public List<CollectorCoTo> getAllByNetworkId(String networkId) {
        OmcpClient omcpClient = this.connection.getConnection();

        String uri = String.format(this.sensorNetConfig.getNetworkIdCollectorsUri(), networkId);

        Response response = omcpClient.doGet(uri);
        CollectorCoTo[] collectorCoToArray = response.getContent(CollectorCoTo[].class);
        List<CollectorCoTo> collectorCoToList = Arrays.asList(collectorCoToArray);
        return collectorCoToList;
    }
}
