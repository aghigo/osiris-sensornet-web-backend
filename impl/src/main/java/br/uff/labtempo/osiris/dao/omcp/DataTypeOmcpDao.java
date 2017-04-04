package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Component("dataTypeOmcpDao")
public class DataTypeOmcpDao implements DataTypeRepository {

    @Autowired
    private VirtualSensorNetConfig virtualSensorNetConfig;

    @Autowired
    private VirtualSensorNetConnection virtualSensorNetConnection;

    @Override
    public DataTypeVsnTo getById(String id) throws AbstractClientRuntimeException, AbstractRequestException {
        OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
        String uri = String.format(this.virtualSensorNetConfig.getDataTypeIdUri(), id);
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
        DataTypeVsnTo dataTypeVsnTo = response.getContent(DataTypeVsnTo.class);
        return dataTypeVsnTo;
    }

    @Override
    public List<DataTypeVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException {
        OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
        String uri = this.virtualSensorNetConfig.getDataTypesUri();
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
        DataTypeVsnTo[] dataTypeVsnToArray = response.getContent(DataTypeVsnTo[].class);
        List<DataTypeVsnTo> dataTypeVsnToList = Arrays.asList(dataTypeVsnToArray);
        return dataTypeVsnToList;
    }

    @Override
    public URI insert(DataTypeVsnTo dataTypeVsnTo) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException {
        OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
        String uri = this.virtualSensorNetConfig.getDataTypesUri();
        Response response;
        try {
            response = omcpClient.doPost(uri, dataTypeVsnTo);
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
        return new URI(response.getLocation());
    }

    @Override
    public void update(String id, DataTypeVsnTo dataTypeVsnTo) throws AbstractClientRuntimeException, AbstractRequestException {

    }

    @Override
    public void delete(String id, DataTypeVsnTo dataTypeVsnTo) throws AbstractClientRuntimeException, AbstractRequestException {

    }
}
