package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetModuleConfig;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.repository.VirtualSensorRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DAO class for VirtualSensor (vsensor) CRUD on VirtualSensorNet module
 * a VirtualSensor (a.k.a vsensor) is an abstraction of all VirtualSensorNet sensor types (Link, Composite, Blending)
 * @see VirtualSensorVsnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component("virtualSensorOmpDao")
public class VirtualSensorOmcpDao implements VirtualSensorRepository {
    @Autowired
    private VirtualSensorNetConnection virtualSensorNetConnection;

    @Autowired
    private VirtualSensorNetModuleConfig virtualSensorNetModuleConfig;

    /**
     * Get a VirtualSensor from VirtualSensorNet module based on its unique Id
     * @param virtualSensorId
     * @return VirtualSensorVsnTo
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     */
    @Override
    public VirtualSensorVsnTo getById(String virtualSensorId) throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = String.format(this.virtualSensorNetModuleConfig.getVirtualSensorIdUri(), virtualSensorId);
            Response response = omcpClient.doGet(uri);
            OmcpUtil.handleOmcpResponse(response);
            VirtualSensorVsnTo virtualSensorVsnTo = response.getContent(VirtualSensorVsnTo.class);
            return virtualSensorVsnTo;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    /**
     * Get a list of all available VirtualSensors from VirtualSensorNet module
     * @return List of VirtualSensorVsnTo
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     */
    @Override
    public List<VirtualSensorVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException {
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetModuleConfig.getVirtualSensorUri();
            Response response = omcpClient.doGet(uri);
            if(response.getStatusCode().equals(StatusCode.NOT_FOUND)) {
                return new ArrayList<>();
            }
            OmcpUtil.handleOmcpResponse(response);
            VirtualSensorVsnTo[] virtualSensorVsnToArray = response.getContent(VirtualSensorVsnTo[].class);
            List<VirtualSensorVsnTo> virtualSensorVsnToList = Arrays.asList(virtualSensorVsnToArray);
            return virtualSensorVsnToList;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }
}
