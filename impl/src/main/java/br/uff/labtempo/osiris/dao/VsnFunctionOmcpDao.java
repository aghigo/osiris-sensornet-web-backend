package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetModuleConfig;
import br.uff.labtempo.osiris.factory.connection.OsirisConnectionFactory;
import br.uff.labtempo.osiris.repository.VsnFunctionRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * OMCP DAO for GET/POST/PUT/DELETE FunctionVsnTo from/into VirtualSensorNet Module using OSIRIS' OMCP Client library.
 * @see br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo
 * @author andre.ghigo
 * @version 1.0
 * @since 10/06/17.
 */
@Component("vsnFunctionOmcpDao")
public class VsnFunctionOmcpDao implements VsnFunctionRepository {

    @Autowired
    private OsirisConnectionFactory osirisConnectionFactory;

    @Autowired
    private VirtualSensorNetModuleConfig virtualSensorNetModuleConfig;

    @Override
    public List<FunctionVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException {
        OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
        String uri = this.virtualSensorNetModuleConfig.getVsnFunctionsUri();
        Response omcpResponse = omcpClient.doGet(uri);
        this.osirisConnectionFactory.closeConnection(omcpClient);
        OmcpUtil.handleOmcpResponse(omcpResponse);
        FunctionVsnTo[] functionVsnToArray = omcpResponse.getContent(FunctionVsnTo[].class);
        List<FunctionVsnTo> functionVsnToList = Arrays.asList(functionVsnToArray);
        return functionVsnToList;
    }

    @Override
    public FunctionVsnTo getById(long functionId) throws AbstractRequestException {
        OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
        String uri = String.format(this.virtualSensorNetModuleConfig.getVsnFunctionIdUri(), functionId);
        Response omcpResponse = omcpClient.doGet(uri);
        this.osirisConnectionFactory.closeConnection(omcpClient);
        OmcpUtil.handleOmcpResponse(omcpResponse);
        FunctionVsnTo functionVsnTo = omcpResponse.getContent(FunctionVsnTo.class);
        return functionVsnTo;
    }

    @Override
    public URI create(FunctionVsnTo functionVsnTo) throws AbstractRequestException, URISyntaxException {
        OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
        String uri = this.virtualSensorNetModuleConfig.getVsnFunctionsUri();
        Response omcpResponse = omcpClient.doPost(uri, functionVsnTo);
        this.osirisConnectionFactory.closeConnection(omcpClient);
        OmcpUtil.handleOmcpResponse(omcpResponse);
        return new URI(omcpResponse.getLocation());
    }

    @Override
    public void update(long functionId, FunctionVsnTo functionVsnTo) throws AbstractRequestException {
        OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
        String uri = String.format(this.virtualSensorNetModuleConfig.getVsnFunctionIdUri(), functionId);
        Response omcpResponse = omcpClient.doPut(uri, functionVsnTo);
        this.osirisConnectionFactory.closeConnection(omcpClient);
        OmcpUtil.handleOmcpResponse(omcpResponse);
    }

    @Override
    public void delete(long functionId) throws AbstractRequestException {
        OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
        String uri = String.format(this.virtualSensorNetModuleConfig.getVsnFunctionIdUri(), functionId);
        Response omcpResponse = omcpClient.doDelete(uri);
        this.osirisConnectionFactory.closeConnection(omcpClient);
        OmcpUtil.handleOmcpResponse(omcpResponse);
    }
}
