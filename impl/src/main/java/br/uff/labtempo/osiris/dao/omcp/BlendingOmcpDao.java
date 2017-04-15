package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.BlendingRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
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
            OmcpUtil.handleOmcpResponse(response);
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
            OmcpUtil.handleOmcpResponse(response);
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
            OmcpUtil.handleOmcpResponse(response);
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
            OmcpUtil.handleOmcpResponse(response);
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
            OmcpUtil.handleOmcpResponse(response);
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }
}
