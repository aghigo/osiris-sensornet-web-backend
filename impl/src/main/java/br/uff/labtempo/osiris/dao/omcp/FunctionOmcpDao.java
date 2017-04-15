package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.FunctionConfig;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.FunctionConnection;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.FunctionRepository;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component("functionOmcpDao")
public class FunctionOmcpDao implements FunctionRepository {

    @Autowired
    FunctionConfig functionConfig;

    @Autowired
    FunctionConnection functionConnection;

    @Autowired
    VirtualSensorNetConfig virtualSensorNetConfig;

    @Autowired
    VirtualSensorNetConnection virtualSensorNetConnection;

    @Override
    public InterfaceFnTo getInterface(String functionName) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.functionConnection.getConnection();
            String uri = String.format(this.functionConfig.getFunctionInterfaceUri(), functionName);
            Response response = omcpClient.doGet(uri);
            OmcpUtil.handleOmcpResponse(response);
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
            OmcpUtil.handleOmcpResponse(response);
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
            OmcpUtil.handleOmcpResponse(response);
            FunctionVsnTo functionVsnTo = response.getContent(FunctionVsnTo.class);
            return functionVsnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    @Override
    public List<FunctionVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.functionConnection.getConnection();
            List<FunctionVsnTo> functionVsnToList = new ArrayList<>();
            for(String functionName : this.functionConfig.getFunctionNames()) {
                String uri = String.format(this.functionConfig.getFunctionUri(), functionName);
                Response response = omcpClient.doGet(uri);
                if(response.getStatusCode().equals(StatusCode.OK)) {
                    FunctionVsnTo functionVsnTo = response.getContent(FunctionVsnTo.class);
                    if(functionVsnTo != null) {
                        functionVsnToList.add(functionVsnTo);
                    }
                }
            }
            return functionVsnToList;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }
}
