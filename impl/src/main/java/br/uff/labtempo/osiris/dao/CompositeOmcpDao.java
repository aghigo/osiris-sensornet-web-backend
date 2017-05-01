package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.CompositeRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
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
    private VirtualSensorNetConfig virtualSensorNetConfig;

    @Autowired
    private VirtualSensorNetConnection virtualSensorNetConnection;

    /**
     * Get a specific Composite sensor from VirtualSensorNet module based on a unique Id
     * @param compositeId
     * @return CompositeVsnTo
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public CompositeVsnTo getById(String compositeId) throws AbstractRequestException, AbstractClientRuntimeException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getCompositeIdUri(), compositeId);
            Response response = omcpClient.doGet(uri);
            OmcpUtil.handleOmcpResponse(response);
            CompositeVsnTo compositeVsnTo = response.getContent(CompositeVsnTo.class);
            return compositeVsnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
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
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetConfig.getCompositesUri();
            Response response = omcpClient.doGet(uri);
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
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetConfig.getCompositesUri();
            Response response = omcpClient.doPost(uri, compositeVsnTo);
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
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getCompositeIdUri(), compositeId);
            Response response = omcpClient.doPut(uri, compositeVsnTo);
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
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getCompositeIdUri(), compositeId);
            Response response = omcpClient.doDelete(uri);
            OmcpUtil.handleOmcpResponse(response);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }
}
