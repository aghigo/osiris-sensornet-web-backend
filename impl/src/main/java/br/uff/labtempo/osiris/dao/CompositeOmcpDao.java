package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.omcp.common.exceptions.client.ConnectionException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetModuleConfig;

import br.uff.labtempo.osiris.factory.connection.OsirisConnectionFactory;
import br.uff.labtempo.osiris.repository.CompositeRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DAO class for Composite sensor CRUD on VirtualSensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component("compositeOmcpDao")
public class CompositeOmcpDao implements CompositeRepository {

    @Autowired
    private VirtualSensorNetModuleConfig virtualSensorNetModuleConfig;

    @Autowired
    private OsirisConnectionFactory osirisConnectionFactory;

    /**
     * Get a specific Composite sensor from VirtualSensorNet module based on a unique Id
     * @param compositeId
     * @return CompositeVsnTo
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public CompositeVsnTo getById(String compositeId) throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
        String uri = String.format(this.virtualSensorNetModuleConfig.getCompositeIdUri(), compositeId);
        Response response = omcpClient.doGet(uri);
        this.osirisConnectionFactory.closeConnection(omcpClient);
        OmcpUtil.handleOmcpResponse(response);
        CompositeVsnTo compositeVsnTo = response.getContent(CompositeVsnTo.class);
        return compositeVsnTo;
    }

    /**
     * Get a list of all available Composite sensors from VirtualSensorNet module
     * @return List of CompositeVsnTo
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public List<CompositeVsnTo> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        try {
            OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
            String uri = this.virtualSensorNetModuleConfig.getCompositesUri();
            Response response = omcpClient.doGet(uri);
            this.osirisConnectionFactory.closeConnection(omcpClient);
            if(response.getStatusCode().equals(StatusCode.NOT_FOUND)) {
                return new ArrayList<>();
            }
            OmcpUtil.handleOmcpResponse(response);
            CompositeVsnTo[] compositeVsnToArray = response.getContent(CompositeVsnTo[].class);
            List<CompositeVsnTo> compositeVsnToList = Arrays.asList(compositeVsnToArray);
            return compositeVsnToList;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    /**
     * Create a new Composite sensor on VirtualSensorNet module
     * @param compositeVsnTo
     * @return URI with the new Composite location
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     * @throws URISyntaxException
     */
    @Override
    public URI create(CompositeVsnTo compositeVsnTo) throws AbstractRequestException, AbstractClientRuntimeException, URISyntaxException {
        try {
            OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
            String uri = this.virtualSensorNetModuleConfig.getCompositesUri();
            Response response = omcpClient.doPost(uri, compositeVsnTo);
            this.osirisConnectionFactory.closeConnection(omcpClient);
            OmcpUtil.handleOmcpResponse(response);
            return new URI(response.getLocation());
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    /**
     * Updates an existing Composite sensor from VirtualSensorNet module
     * @param compositeId (Composite sensor to be updated)
     * @param compositeVsnTo (new Data to update)
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public void update(String compositeId, CompositeVsnTo compositeVsnTo) throws AbstractRequestException, AbstractClientRuntimeException {
        try {
            OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
            String uri = String.format(this.virtualSensorNetModuleConfig.getCompositeIdUri(), compositeId);
            Response response = omcpClient.doPut(uri, compositeVsnTo);
            this.osirisConnectionFactory.closeConnection(omcpClient);
            OmcpUtil.handleOmcpResponse(response);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    /**
     * Removes an existing Composite sensor from VirtualSensorNet module
     * @param compositeId (Composite sensor to be removed)
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public void delete(String compositeId) throws AbstractRequestException, AbstractClientRuntimeException {
        try {
            OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
            String uri = String.format(this.virtualSensorNetModuleConfig.getCompositeIdUri(), compositeId);
            Response response = omcpClient.doDelete(uri);
            this.osirisConnectionFactory.closeConnection(omcpClient);
            OmcpUtil.handleOmcpResponse(response);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }
}
