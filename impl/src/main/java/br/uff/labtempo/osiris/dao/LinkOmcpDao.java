package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetModuleConfig;
import br.uff.labtempo.osiris.factory.connection.OsirisConnectionFactory;
import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * DAO class for Link sensor CRUD on VirtualSensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component("linkOmcpDao")
public class LinkOmcpDao implements LinkRepository {

    @Autowired
    private VirtualSensorNetModuleConfig virtualSensorNetModuleConfig;

    @Autowired
    private OsirisConnectionFactory osirisConnectionFactory;

    /**
     * Get a specific Link sensor from VirtualSensorNet module by its unique Id
     * @param id
     * @return LinkVsnTo
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public LinkVsnTo getById(long id) throws AbstractRequestException, AbstractClientRuntimeException {
        String uri = String.format(this.virtualSensorNetModuleConfig.getLinkIdUri(), id);
        try {
            OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
            Response response = omcpClient.doGet(uri);
            this.osirisConnectionFactory.closeConnection(omcpClient);
            OmcpUtil.handleOmcpResponse(response);
            LinkVsnTo linkVsnTo = response.getContent(LinkVsnTo.class);
            return linkVsnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    /**
     * Get a list of all available Link Sensors from VirtualSensorNet module
     * @return List of LinkVsnTo
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public List<LinkVsnTo> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        String uri = this.virtualSensorNetModuleConfig.getLinksUri();
        try {
            OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
            Response response = omcpClient.doGet(uri);
            this.osirisConnectionFactory.closeConnection(omcpClient);
            if(response.getStatusCode().equals(StatusCode.NOT_FOUND)) {
                return new ArrayList<>();
            }
            OmcpUtil.handleOmcpResponse(response);
            LinkVsnTo[] linkVsnToArray = response.getContent(LinkVsnTo[].class);
            List<LinkVsnTo> linkVsnToList = Arrays.asList(linkVsnToArray);
            this.sortById(linkVsnToList);
            return linkVsnToList;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    /**
     * Create a new Link sensor on VirtualSensorNet module
     * @param linkVsnTo
     * @return URI with the new Link sensor location
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     * @throws URISyntaxException
     */
    @Override
    public URI save(LinkVsnTo linkVsnTo) throws AbstractRequestException, AbstractClientRuntimeException, URISyntaxException {
        OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
        String uri = this.virtualSensorNetModuleConfig.getLinksUri();
        Response response;
        try {
            response = omcpClient.doPost(uri, linkVsnTo);
            this.osirisConnectionFactory.closeConnection(omcpClient);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
        return new URI(response.getLocation());
    }

    /**
     * Update an existing Link sensor from VirtualSensorNet module
     * @param id (id of the Link Sensor to be updated)
     * @param linkVsnTo (Data to update)
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public void update(long id, LinkVsnTo linkVsnTo) throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
        String uri = String.format(this.virtualSensorNetModuleConfig.getLinkIdUri(), id);
        Response response;
        try {
            response = omcpClient.doPut(uri, linkVsnTo);
            this.osirisConnectionFactory.closeConnection(omcpClient);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
    }

    /**
     * Removes an existing Link sensor from VirtualSensorNet module
     * @param id (Link sensor to be removed)
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public void delete(long id) throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
        String uri = String.format(this.virtualSensorNetModuleConfig.getLinkIdUri(), id);
        Response response;
        try {
            response = omcpClient.doDelete(uri);
            this.osirisConnectionFactory.closeConnection(omcpClient);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
        OmcpUtil.handleOmcpResponse(response);
    }

    /**
     * Get a list of all available FieldTo from all LinkSensors from VirtualSensorNet module
     * A Field is composed by its Id and related DataType
     * @see DataTypeOmcpDao
     * @see FieldTo
     * @return List of FieldTo
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @Override
    public List<FieldTo> getAllFields() throws AbstractRequestException, AbstractClientRuntimeException {
        List<FieldTo> fieldToList = new ArrayList<>();
        for(LinkVsnTo linkVsnTo : getAll()) {
            fieldToList.addAll(linkVsnTo.getFields());
        }
        return fieldToList;
    }

    public void sortById(List<LinkVsnTo> linkVsnToList) {
        Comparator<LinkVsnTo> comparator = new Comparator<LinkVsnTo>() {
            @Override
            public int compare(LinkVsnTo o1, LinkVsnTo o2) {
                long id1 = o1.getId();
                long id2 = o2.getId();
                if(id1 < id2) {
                    return -1;
                } else if(id1 > id2) {
                    return 1;
                }
                return 0;
            }
        };
        Collections.sort(linkVsnToList, comparator);
    }
}