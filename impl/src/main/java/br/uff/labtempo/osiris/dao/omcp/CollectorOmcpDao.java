package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.repository.CollectorRepository;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * DAO class for Collector CRUD on SensorNet
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component("collectorOmcpDao")
public class CollectorOmcpDao implements CollectorRepository {

    @Autowired
    private SensorNetConfig sensorNetConfig;

    @Autowired
    private SensorNetConnection connection;

    /**
     * Get an specific Collector based on Network id and Collector Id
     * @param networkId
     * @param collectorId
     * @return CollectorSnTo
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public CollectorSnTo getByNetworkId(String networkId, String collectorId) throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdCollectorIdUri(), networkId, collectorId);
        Response response;
        try {
            response = omcpClient.doGet(uri);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
        CollectorSnTo collectorSnTo = response.getContent(CollectorSnTo.class);
        return collectorSnTo;
    }

    /**
     * Get a list of all available Collectors from a specific Network
     * @param networkId
     * @return List of CollectorSnTo
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public List<CollectorSnTo> getAllByNetworkId(String networkId) throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.connection.getConnection();

        String uri = String.format(this.sensorNetConfig.getNetworkIdCollectorsUri(), networkId);
        Response response;
        try {
            response = omcpClient.doGet(uri);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
        CollectorSnTo[] collectorSnToArray = response.getContent(CollectorSnTo[].class);
        List<CollectorSnTo> collectorSnToList = Arrays.asList(collectorSnToArray);
        return collectorSnToList;
    }
}
