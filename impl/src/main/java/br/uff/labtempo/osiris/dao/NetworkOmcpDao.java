package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.SensorNetModuleConfig;
import br.uff.labtempo.osiris.factory.connection.OsirisConnectionFactory;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DAO class for Network CRUD on SensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component("networkOmcpDao")
public class NetworkOmcpDao implements NetworkRepository {
    @Autowired
    private SensorNetModuleConfig sensorNetModuleConfig;

    @Autowired
    private OsirisConnectionFactory connection;

    /**
     * Get a specifc Network from SensorNet module based on its unique id
     * @param id
     * @return NetworkSnTo
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public NetworkSnTo getById(String id) throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetModuleConfig.getNetworkIdUri(), id);
        Response response;

        try {
            response = omcpClient.doGet(uri);
            this.connection.closeConnection(omcpClient);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }

        OmcpUtil.handleOmcpResponse(response);

        NetworkSnTo networkSnTo = response.getContent(NetworkSnTo.class);
        return networkSnTo;
    }

    /**
     * Get a list of all available Networks from SensorNet module
     * @return List of NetworkSnTo
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public List<NetworkSnTo> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = this.sensorNetModuleConfig.getNetworksUri();
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

        NetworkSnTo[] networkSnToArray = response.getContent(NetworkSnTo[].class);
        List<NetworkSnTo> networkSnToList = Arrays.asList(networkSnToArray);
        return networkSnToList;
    }
}
