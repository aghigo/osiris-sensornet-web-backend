package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.OmpRabbitExceptionThrowerClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.omcp.common.exceptions.client.UnreachableModuleException;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("networkOmcpDao")
public class NetworkOmcpDao implements NetworkRepository {
    @Autowired
    private SensorNetConfig sensorNetConfig;

    @Autowired
    private SensorNetConnection connection;

    @Override
    public NetworkSnTo getById(String id) throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = String.format(this.sensorNetConfig.getNetworkIdUri(), id);
        Response response;

        try {
            response = omcpClient.doGet(uri);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }

        switch(response.getStatusCode()) {
            case NOT_FOUND:
                throw new NotFoundException();
            case BAD_REQUEST:
                throw new BadRequestException();
            case REQUEST_TIMEOUT:
                throw new RequestTimeoutException();
            case INTERNAL_SERVER_ERROR:
                throw new InternalServerErrorException();
            case FORBIDDEN:
                throw new ForbiddenException();
            case METHOD_NOT_ALLOWED:
                throw new MethodNotAllowedException();
            case NOT_IMPLEMENTED:
                throw new NotImplementedException();
        }

        NetworkSnTo networkSnTo = response.getContent(NetworkSnTo.class);
        return networkSnTo;
    }

    @Override
    public List<NetworkSnTo> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        OmcpClient omcpClient = this.connection.getConnection();
        String uri = this.sensorNetConfig.getNetworksUri();
        Response response;

        try {
            response = omcpClient.doGet(uri);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }

        switch(response.getStatusCode()) {
            case NOT_FOUND:
                throw new NotFoundException();
            case BAD_REQUEST:
                throw new BadRequestException();
            case REQUEST_TIMEOUT:
                throw new RequestTimeoutException();
            case INTERNAL_SERVER_ERROR:
                throw new InternalServerErrorException();
            case FORBIDDEN:
                throw new ForbiddenException();
            case METHOD_NOT_ALLOWED:
                throw new MethodNotAllowedException();
            case NOT_IMPLEMENTED:
                throw new NotImplementedException();
        }

        NetworkSnTo[] networkSnToArray = response.getContent(NetworkSnTo[].class);
        List<NetworkSnTo> networkSnToList = Arrays.asList(networkSnToArray);
        return networkSnToList;
    }
}
