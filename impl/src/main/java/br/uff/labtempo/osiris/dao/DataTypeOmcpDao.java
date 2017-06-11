package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetModuleConfig;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("dataTypeOmcpDao")
public class DataTypeOmcpDao implements DataTypeRepository {

    @Autowired
    private VirtualSensorNetModuleConfig virtualSensorNetModuleConfig;

    @Autowired
    private VirtualSensorNetConnection virtualSensorNetConnection;

    @Override
    public DataTypeVsnTo getById(long id) throws AbstractClientRuntimeException, AbstractRequestException {
        OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
        String uri = String.format(this.virtualSensorNetModuleConfig.getDataTypeIdUri(), id);
        Response response;
        try {
            response = omcpClient.doGet(uri);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
        DataTypeVsnTo dataTypeVsnTo = response.getContent(DataTypeVsnTo.class);
        return dataTypeVsnTo;
    }

    @Override
    public List<DataTypeVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException {
        OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
        String uri = this.virtualSensorNetModuleConfig.getDataTypesUri();
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
        DataTypeVsnTo[] dataTypeVsnToArray = response.getContent(DataTypeVsnTo[].class);
        List<DataTypeVsnTo> dataTypeVsnToList = Arrays.asList(dataTypeVsnToArray);
        return dataTypeVsnToList;
    }

    @Override
    public URI insert(DataTypeVsnTo dataTypeVsnTo) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException {
        OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
        String uri = this.virtualSensorNetModuleConfig.getDataTypesUri();
        Response response;
        try {
            response = omcpClient.doPost(uri, dataTypeVsnTo);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
        return new URI(response.getLocation());
    }

    @Override
    public void update(String id, DataTypeVsnTo dataTypeVsnTo) throws AbstractClientRuntimeException, AbstractRequestException {
        OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
        String uri = String.format(this.virtualSensorNetModuleConfig.getDataTypeIdUri(), id);
        Response response;
        try {
            response = omcpClient.doPut(uri, dataTypeVsnTo);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
    }

    @Override
    public void delete(String id) throws AbstractClientRuntimeException, AbstractRequestException {
        OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
        String uri = String.format(this.virtualSensorNetModuleConfig.getDataTypeIdUri(), id);
        Response response;
        try {
            response = omcpClient.doDelete(uri);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
    }
}
