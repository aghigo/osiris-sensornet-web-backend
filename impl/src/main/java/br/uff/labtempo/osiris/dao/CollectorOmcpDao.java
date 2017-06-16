package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.SensorNetModuleConfig;
import br.uff.labtempo.osiris.factory.connection.OsirisConnectionFactory;
import br.uff.labtempo.osiris.repository.CollectorRepository;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private SensorNetModuleConfig sensorNetModuleConfig;

    @Autowired
    private OsirisConnectionFactory connection;

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
        String uri = String.format(this.sensorNetModuleConfig.getNetworkIdCollectorIdUri(), networkId, collectorId);
        Response response;
        try {
            response = omcpClient.doGet(uri);
            this.connection.closeConnection(omcpClient);
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

        String uri = String.format(this.sensorNetModuleConfig.getNetworkIdCollectorsUri(), networkId);
        Response response;
        try {
            response = omcpClient.doGet(uri);
            this.connection.closeConnection(omcpClient);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        if(response.getStatusCode().equals(StatusCode.NOT_FOUND)) {
            return new ArrayList<>();
        }
        OmcpUtil.handleOmcpResponse(response);
        CollectorSnTo[] collectorSnToArray = response.getContent(CollectorSnTo[].class);
        List<CollectorSnTo> collectorSnToList = Arrays.asList(collectorSnToArray);
        return collectorSnToList;
    }
}
