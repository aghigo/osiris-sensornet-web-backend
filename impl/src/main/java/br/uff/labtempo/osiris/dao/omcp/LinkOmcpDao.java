package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("linkOmcpDao")
public class LinkOmcpDao implements LinkRepository {

    @Autowired
    private VirtualSensorNetConfig virtualSensorNetConfig;

    @Autowired
    private VirtualSensorNetConnection virtualSensorNetConnection;

    @Override
    public LinkVsnTo getById(String id) throws AbstractRequestException, AbstractClientRuntimeException {
        String uri = String.format(this.virtualSensorNetConfig.getLinksUri(), id);
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            Response response = omcpClient.doGet(uri);
            switch(response.getStatusCode()) {
                case BAD_REQUEST:
                    throw new BadRequestException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case NOT_FOUND:
                    throw new NotFoundException();
            }
            LinkVsnTo linkVsnTo = response.getContent(LinkVsnTo.class);
            return linkVsnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public List<LinkVsnTo> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        String uri = this.virtualSensorNetConfig.getLinksUri();
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            Response response = omcpClient.doGet(uri);
            switch(response.getStatusCode()) {
                case BAD_REQUEST:
                    throw new BadRequestException();
                case FORBIDDEN:
                    throw new ForbiddenException();
                case METHOD_NOT_ALLOWED:
                    throw new MethodNotAllowedException();
                case REQUEST_TIMEOUT:
                    throw new RequestTimeoutException();
                case NOT_IMPLEMENTED:
                    throw new NotImplementedException();
                case INTERNAL_SERVER_ERROR:
                    throw new InternalServerErrorException();
                case NOT_FOUND:
                    throw new NotFoundException();
            }
            LinkVsnTo[] linkVsnToArray = response.getContent(LinkVsnTo[].class);
            List<LinkVsnTo> linkVsnToList = Arrays.asList(linkVsnToArray);
            return linkVsnToList;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public void save(LinkVsnTo linkVsnTo) {

    }

    @Override
    public void update(String id, LinkVsnTo linkVsnTo) {

    }

    @Override
    public void delete(String id) {

    }
}
