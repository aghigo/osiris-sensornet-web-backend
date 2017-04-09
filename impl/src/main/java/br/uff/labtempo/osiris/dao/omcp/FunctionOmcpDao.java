package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.FunctionConfig;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.FunctionRepository;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component("functionOmcpDao")
public class FunctionOmcpDao implements FunctionRepository {

    @Autowired
    FunctionConfig functionConfig;

    @Autowired
    VirtualSensorNetConfig virtualSensorNetConfig;

    @Autowired
    VirtualSensorNetConnection virtualSensorNetConnection;

    @Override
    public InterfaceFnTo getInterface(String functionName) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.functionConfig.getFunctionInterfaceUri(), functionName);
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
            InterfaceFnTo interfaceFnTo = response.getContent(InterfaceFnTo.class);
            return interfaceFnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public URI createOnVirtualSensorNet(InterfaceFnTo interfaceFnTo) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetConfig.getFunctionUri();
            FunctionVsnTo functionVsnTo = new FunctionVsnTo(interfaceFnTo);
            Response response = omcpClient.doPost(uri, functionVsnTo);
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
            return new URI(response.getLocation());
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public FunctionVsnTo getFromVirtualSensorNet(String functionName) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetConfig.getFunctionNameUri(), functionName);
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
            FunctionVsnTo functionVsnTo = response.getContent(FunctionVsnTo.class);
            return functionVsnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }
}
