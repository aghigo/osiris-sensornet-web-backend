package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.repository.OmcpRepository;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component("sensorNetOmcpDao")
public class SensorNetOmcpDao<T> implements OmcpRepository<T> {

    private SensorNetConnection sensorNetConnection;

    @Autowired
    public SensorNetOmcpDao(SensorNetConnection sensorNetConnection) {
        this.sensorNetConnection = sensorNetConnection;
    }

    public T doGet(String uri) throws AbstractRequestException, AbstractClientRuntimeException {
        Response omcpResponse = this.sensorNetConnection.getConnection().doGet(uri);
        OmcpUtil.handleOmcpResponse(omcpResponse);
        T content = (T) omcpResponse.getContent();
        if(content == null) {
            throw new NotFoundException();
        }
        return content;
    }

    public URI doPost(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException, URISyntaxException {
        Response omcpResponse = this.sensorNetConnection.getConnection().doPost(uri, t);
        OmcpUtil.handleOmcpResponse(omcpResponse);
        return new URI(omcpResponse.getLocation());
    }

    public void doPut(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException {
        Response omcpResponse = this.sensorNetConnection.getConnection().doPut(uri, t);
        OmcpUtil.handleOmcpResponse(omcpResponse);
    }

    public void doDelete(String uri) throws AbstractRequestException, AbstractClientRuntimeException {
        Response omcpResponse = this.sensorNetConnection.getConnection().doDelete(uri);
        OmcpUtil.handleOmcpResponse(omcpResponse);
    }

    public void doNotify(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException {
        this.sensorNetConnection.getConnection().doNofity(uri, t);
    }
}
