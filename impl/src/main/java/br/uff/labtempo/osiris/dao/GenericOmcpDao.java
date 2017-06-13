package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;

import br.uff.labtempo.osiris.factory.connection.SensorNetConnectionFactory;
import br.uff.labtempo.osiris.repository.OmcpRepository;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * DAO class for generic T CRUD on SensorNet using the OMCP protocol of the OSIRIS API library
 * @param <T>
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component("genericOmcpDao")
public class GenericOmcpDao<T> implements OmcpRepository<T> {

    private SensorNetConnectionFactory sensorNetConnection;

    @Autowired
    public GenericOmcpDao(SensorNetConnectionFactory sensorNetConnectionFactory) {
        this.sensorNetConnection = sensorNetConnectionFactory;
    }

    /**
     * Get an T object from SensorNet module based on a valid URI location
     * @param uri
     * @return <T>
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public T doGet(String uri) throws AbstractRequestException, AbstractClientRuntimeException {
        Response omcpResponse = this.sensorNetConnection.getConnection().doGet(uri);
        OmcpUtil.handleOmcpResponse(omcpResponse);
        T content = (T) omcpResponse.getContent();
        if(content == null) {
            throw new NotFoundException();
        }
        return content;
    }

    /**
     * Creates a new valid generic T object on SensorNet module based on a valid URI location
     * @param uri
     * @param t
     * @return URI with the new T location
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     * @throws URISyntaxException
     */
    public URI doPost(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException, URISyntaxException {
        Response omcpResponse = this.sensorNetConnection.getConnection().doPost(uri, t);
        OmcpUtil.handleOmcpResponse(omcpResponse);
        return new URI(omcpResponse.getLocation());
    }

    /**
     * Update an existing generic and valid T object from SensorNet module based on a valid URI location
     * @param uri
     * @param t
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public void doPut(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException {
        Response omcpResponse = this.sensorNetConnection.getConnection().doPut(uri, t);
        OmcpUtil.handleOmcpResponse(omcpResponse);
    }

    /**
     * Removes an existing generic T object from SensorNet based on a valid URI location
     * @param uri
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public void doDelete(String uri) throws AbstractRequestException, AbstractClientRuntimeException {
        Response omcpResponse = this.sensorNetConnection.getConnection().doDelete(uri);
        OmcpUtil.handleOmcpResponse(omcpResponse);
    }

    /**
     * Notify and generic and valid T object on SensorNet module based on a valid messageGroup URI
     * @param uri (messageGroup) e.g. collector.messagegroup.osiris
     * @param t
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public void doNotify(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException {
        this.sensorNetConnection.getConnection().doNofity(uri, t);
    }
}
