package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.BlendingRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Component("blendingOmcpDao")
public class BlendingOmcpDao implements BlendingRepository {

    @Autowired
    private VirtualSensorNetConfig virtualSensorNetConfig;

    @Autowired
    private VirtualSensorNetConnection virtualSensorNetConnection;

    @Override
    public List<BlendingVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetConfig.getBlendingsUri();
            Response response = omcpClient.doGet(uri);
            switch(response.getStatusCode()) {
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case BAD_REQUEST:
                    throw new BadRequestException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case NOT_FOUND:
                    throw new NotFoundException();
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
            }
            BlendingVsnTo[] blendingVsnToArray = response.getContent(BlendingVsnTo[].class);
            List<BlendingVsnTo> blendingVsnToList = Arrays.asList(blendingVsnToArray);
            return blendingVsnToList;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public BlendingVsnTo getById(long blendingId) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getBlendingsUri(), blendingId);
            Response response = omcpClient.doGet(uri);
            switch(response.getStatusCode()) {
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case BAD_REQUEST:
                    throw new BadRequestException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case NOT_FOUND:
                    throw new NotFoundException();
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
            }
            BlendingVsnTo blendingVsnTo = response.getContent(BlendingVsnTo.class);
            return blendingVsnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public URI create(BlendingVsnTo blendingVsnTo) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetConfig.getBlendingsUri();
            Response response = omcpClient.doPost(uri, blendingVsnTo);
            switch(response.getStatusCode()) {
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case BAD_REQUEST:
                    throw new BadRequestException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case NOT_FOUND:
                    throw new NotFoundException();
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
            }
            URI createdUri = new URI(response.getLocation());
            return createdUri;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public void update(long blendingId, BlendingVsnTo blendingVsnTo) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getBlendingIdUri(), blendingId);
            Response response = omcpClient.doPut(uri, blendingVsnTo);
            switch(response.getStatusCode()) {
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case BAD_REQUEST:
                    throw new BadRequestException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case NOT_FOUND:
                    throw new NotFoundException();
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
            }
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public void delete(long blendingId) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getBlendingIdUri(), blendingId);
            Response response = omcpClient.doDelete(uri);
            switch(response.getStatusCode()) {
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case BAD_REQUEST:
                    throw new BadRequestException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case NOT_FOUND:
                    throw new NotFoundException();
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
            }
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }
}
