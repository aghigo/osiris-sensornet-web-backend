package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.OmcpRepository;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * DAO class for generic T object CRUD on VirtualSensorNet module
 * @param <T>
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component("virtualSensorNetOmcpDao")
public class VirtualSensorNetOmcpDao<T> implements OmcpRepository<T> {

    private VirtualSensorNetConnection virtualSensorNetConnection;

    @Autowired
    public VirtualSensorNetOmcpDao(VirtualSensorNetConnection virtualSensorNetConnection) {
        this.virtualSensorNetConnection = virtualSensorNetConnection;
    }

    /**
     * Get an specific T object from VirtualSensorNet module based on a valid URI location
     * @param uri
     * @return <T> generic object
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public T doGet(String uri) throws AbstractRequestException, AbstractClientRuntimeException {
        Response omcpResponse = this.virtualSensorNetConnection.getConnection().doGet(uri);
        OmcpUtil.handleOmcpResponse(omcpResponse);
        T content = (T) omcpResponse.getContent();
        if(content == null) {
            throw new NotFoundException();
        }
        return content;
    }

    /**
     * Creates a new valid and generic T object on VirtualSensorNet module based on a valid URI location
     * @param uri
     * @param t
     * @return URI of the new T object location
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     * @throws URISyntaxException
     */
    public URI doPost(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException, URISyntaxException {
        Response omcpResponse = this.virtualSensorNetConnection.getConnection().doPost(uri, t);
        OmcpUtil.handleOmcpResponse(omcpResponse);
        return new URI(omcpResponse.getLocation());
    }

    /**
     * Updates an existing generic T object from VirtualSensorNet module based on a valid URI location
     * @param uri
     * @param t
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public void doPut(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException {
        Response omcpResponse = this.virtualSensorNetConnection.getConnection().doPut(uri, t);
        OmcpUtil.handleOmcpResponse(omcpResponse);
    }

    /**
     * Removes an existing generic object from VirtualSensorNet moduel based on a valid URI location
     * @param uri
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public void doDelete(String uri) throws AbstractRequestException, AbstractClientRuntimeException {
        Response omcpResponse = this.virtualSensorNetConnection.getConnection().doDelete(uri);
        OmcpUtil.handleOmcpResponse(omcpResponse);
    }

    /**
     * Notify an generic T object on VirtualSensorNet moduel to a valid messageGroup URI
     * @param uri (messageGroup location) e.g. collector.messagegroup.osiris
     * @param t
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public void doNotify(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException {
        this.virtualSensorNetConnection.getConnection().doNofity(uri, t);
    }
}
