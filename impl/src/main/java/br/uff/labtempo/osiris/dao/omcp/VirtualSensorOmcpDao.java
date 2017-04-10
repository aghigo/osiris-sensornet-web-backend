package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.VirtualSensorRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("virtualSensorOmpDao")
public class VirtualSensorOmcpDao implements VirtualSensorRepository {

    @Autowired
    private VirtualSensorNetConnection virtualSensorNetConnection;

    @Autowired
    private VirtualSensorNetConfig virtualSensorNetConfig;

    @Override
    public VirtualSensorVsnTo getById(String virtualSensorId) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getVirtualSensorIdUri(), virtualSensorId);
            Response response = omcpClient.doGet(uri);
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
            VirtualSensorVsnTo virtualSensorVsnTo = response.getContent(VirtualSensorVsnTo.class);
            return virtualSensorVsnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public List<VirtualSensorVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetConfig.getVirtualSensorUri();
            Response response = omcpClient.doGet(uri);
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
            VirtualSensorVsnTo[] virtualSensorVsnToArray = response.getContent(VirtualSensorVsnTo[].class);
            List<VirtualSensorVsnTo> virtualSensorVsnToList = Arrays.asList(virtualSensorVsnToArray);
            return virtualSensorVsnToList;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }
}
