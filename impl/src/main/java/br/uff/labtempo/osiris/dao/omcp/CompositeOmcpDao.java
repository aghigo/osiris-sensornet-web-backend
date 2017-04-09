package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.CompositeRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Component("compositeOmcpDao")
public class CompositeOmcpDao implements CompositeRepository {

    @Autowired
    private VirtualSensorNetConfig virtualSensorNetConfig;

    @Autowired
    private VirtualSensorNetConnection virtualSensorNetConnection;

    @Override
    public CompositeVsnTo getById(String compositeId) throws AbstractRequestException, AbstractClientRuntimeException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getDataTypeIdUri(), compositeId);
            Response response = omcpClient.doGet(uri);
            switch(response.getStatusCode()) {
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
                case NOT_FOUND:
                    throw new NotFoundException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case BAD_REQUEST:
                    throw new BadRequestException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
            }
            CompositeVsnTo compositeVsnTo = response.getContent(CompositeVsnTo.class);
            return compositeVsnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public List<CompositeVsnTo> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetConfig.getCompositesUri();
            Response response = omcpClient.doGet(uri);
            switch(response.getStatusCode()) {
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
                case NOT_FOUND:
                    throw new NotFoundException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case BAD_REQUEST:
                    throw new BadRequestException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
            }
            CompositeVsnTo[] compositeVsnToArray = response.getContent(CompositeVsnTo[].class);
            List<CompositeVsnTo> compositeVsnToList = Arrays.asList(compositeVsnToArray);
            return compositeVsnToList;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public URI create(CompositeVsnTo compositeVsnTo) throws AbstractRequestException, AbstractClientRuntimeException, URISyntaxException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetConfig.getCompositesUri();
            Response response = omcpClient.doPost(uri, compositeVsnTo);
            switch(response.getStatusCode()) {
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
                case NOT_FOUND:
                    throw new NotFoundException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case BAD_REQUEST:
                    throw new BadRequestException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
            }
            return new URI(response.getLocation());
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public void update(String compositeId, CompositeVsnTo compositeVsnTo) throws AbstractRequestException, AbstractClientRuntimeException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getCompositeIdUri(), compositeId);
            Response response = omcpClient.doPut(uri, compositeVsnTo);
            switch(response.getStatusCode()) {
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
                case NOT_FOUND:
                    throw new NotFoundException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case BAD_REQUEST:
                    throw new BadRequestException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
            }
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public void delete(String compositeId) throws AbstractRequestException, AbstractClientRuntimeException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getCompositeIdUri(), compositeId);
            Response response = omcpClient.doDelete(uri);
            switch(response.getStatusCode()) {
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
                case NOT_FOUND:
                    throw new NotFoundException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case BAD_REQUEST:
                    throw new BadRequestException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
            }
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }
}
