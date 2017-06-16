package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetModuleConfig;
import br.uff.labtempo.osiris.factory.connection.OsirisConnectionFactory;
import br.uff.labtempo.osiris.repository.BlendingRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DAO class for VirtualSensorNet Blending Sensors CRUD with OMCP protocol using the OSIRIS API library
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component("blendingOmcpDao")
public class BlendingOmcpDao implements BlendingRepository {

    @Autowired
    private VirtualSensorNetModuleConfig virtualSensorNetModuleConfig;

    @Autowired
    private OsirisConnectionFactory connection;

    /**
     * Get all Blending sensors from VirtualSensorNet module
     * @return List of BlendingVsnTo
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     */
    @Override
    public List<BlendingVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.connection.getConnection();
            String uri = this.virtualSensorNetModuleConfig.getBlendingsUri();
            Response response = omcpClient.doGet(uri);
            this.connection.closeConnection(omcpClient);
            if(response.getStatusCode().equals(StatusCode.NOT_FOUND)) {
                return new ArrayList<>();
            }
            OmcpUtil.handleOmcpResponse(response);
            BlendingVsnTo[] blendingVsnToArray = response.getContent(BlendingVsnTo[].class);
            List<BlendingVsnTo> blendingVsnToList = Arrays.asList(blendingVsnToArray);
            return blendingVsnToList;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    /**
     * Get a specific Blending Sensor from VirtualSensorNet based on a unique blendingId.
     * @param blendingId
     * @return BlendingVsnTo
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     */
    @Override
    public BlendingVsnTo getById(long blendingId) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.connection.getConnection();
            String uri = String.format(this.virtualSensorNetModuleConfig.getBlendingIdUri(), blendingId);
            Response response = omcpClient.doGet(uri);
            this.connection.closeConnection(omcpClient);
            OmcpUtil.handleOmcpResponse(response);
            BlendingVsnTo blendingVsnTo = response.getContent(BlendingVsnTo.class);
            return blendingVsnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    /**
     * Create a new Blending sensor on VirtualSensorNet module
     * @param blendingVsnTo
     * @return URI with new Blending location
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     * @throws URISyntaxException
     */
    @Override
    public URI create(BlendingVsnTo blendingVsnTo) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException {
        try {
            OmcpClient omcpClient = this.connection.getConnection();
            String uri = this.virtualSensorNetModuleConfig.getBlendingsUri();
            Response response = omcpClient.doPost(uri, blendingVsnTo);
            this.connection.closeConnection(omcpClient);
            OmcpUtil.handleOmcpResponse(response);
            URI createdUri = new URI(response.getLocation());
            return createdUri;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    /**
     * Update an existing Blending sensor from VirtualSensorNet
     * @param blendingId (sensor to be updated)
     * @param blendingVsnTo (data to be updated)
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     */
    @Override
    public void update(long blendingId, BlendingVsnTo blendingVsnTo) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.connection.getConnection();
            String uri = String.format(this.virtualSensorNetModuleConfig.getBlendingIdUri(), blendingId);
            Response response = omcpClient.doPut(uri, blendingVsnTo);
            this.connection.closeConnection(omcpClient);
            OmcpUtil.handleOmcpResponse(response);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    /**
     * Removes an existing sensor from VirtualSensorNet module
     * @param blendingId (sensor to be removed)
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     */
    @Override
    public void delete(long blendingId) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.connection.getConnection();
            String uri = String.format(this.virtualSensorNetModuleConfig.getBlendingIdUri(), blendingId);
            Response response = omcpClient.doDelete(uri);
            this.connection.closeConnection(omcpClient);
            OmcpUtil.handleOmcpResponse(response);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }
}
