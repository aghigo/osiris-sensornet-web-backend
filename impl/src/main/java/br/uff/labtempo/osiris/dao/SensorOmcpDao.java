package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.SensorNetModuleConfig;
import br.uff.labtempo.osiris.factory.connection.SensorNetConnectionFactory;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("sensorOmcpDao")
public class SensorOmcpDao implements SensorRepository {

    @Autowired
    private SensorNetModuleConfig sensorNetModuleConfig;

    @Autowired
    private SensorNetConnectionFactory connection;

    @Override
    public SensorSnTo getByCollectorIdAndNetworkId(String networkId, String collectorId, String sensorId) throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetModuleConfig.getNetworkIdCollectorIdSensorIdUri(), networkId, collectorId, sensorId);
        Response response;
        try {
            response = omcpClient.doGet(uri);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
        SensorSnTo sensorSnTo = response.getContent(SensorSnTo.class);
        return sensorSnTo;
    }

    @Override
    public List<SensorSnTo> getAllByCollectorIdAndNetworkId(String networkId, String collectorId) throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetModuleConfig.getNetworkIdCollectorIdSensorsUri(), networkId, collectorId);

        Response response;
        try {
            response = omcpClient.doGet(uri);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        if(response.getStatusCode().equals(StatusCode.NOT_FOUND)) {
            return new ArrayList<>();
        }
        OmcpUtil.handleOmcpResponse(response);
        SensorSnTo[] sensorSnToArray = response.getContent(SensorSnTo[].class);
        List<SensorSnTo> sensorSnToList = Arrays.asList(sensorSnToArray);
        return sensorSnToList;
    }

    @Override
    public List<SensorSnTo> getAllByNetworkId(String networkId) throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetModuleConfig.getNetworkIdSensorsUri(), networkId);
        Response response;
        try {
            response = omcpClient.doGet(uri);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
        SensorSnTo[] sensorSnToArray = response.getContent(SensorSnTo[].class);
        List<SensorSnTo> sensorSnToList = Arrays.asList(sensorSnToArray);
        return sensorSnToList;
    }
}