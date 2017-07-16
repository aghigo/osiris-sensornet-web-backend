package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetModuleConfig;
import br.uff.labtempo.osiris.factory.connection.OsirisConnectionFactory;
import br.uff.labtempo.osiris.repository.VirtualSensorRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
    private OsirisConnectionFactory osirisConnectionFactory;

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
        OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
        String uri = String.format(this.virtualSensorNetModuleConfig.getVirtualSensorIdUri(), virtualSensorId);
        Response response = omcpClient.doGet(uri);
        this.osirisConnectionFactory.closeConnection(omcpClient);
        OmcpUtil.handleOmcpResponse(response);
        VirtualSensorVsnTo virtualSensorVsnTo = response.getContent(VirtualSensorVsnTo.class);
        return virtualSensorVsnTo;

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
            OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
            String uri = this.virtualSensorNetModuleConfig.getVirtualSensorUri();
            Response response = omcpClient.doGet(uri);
            this.osirisConnectionFactory.closeConnection(omcpClient);
            if(response.getStatusCode().equals(StatusCode.NOT_FOUND)) {
                return new ArrayList<>();
            }
            OmcpUtil.handleOmcpResponse(response);
            VirtualSensorVsnTo[] virtualSensorVsnToArray = response.getContent(VirtualSensorVsnTo[].class);
            List<VirtualSensorVsnTo> virtualSensorVsnToList = Arrays.asList(virtualSensorVsnToArray);
            this.sortById(virtualSensorVsnToList);
            return virtualSensorVsnToList;
        } catch (AbstractClientRuntimeException e) {
            throw e;
        }
    }

    public void sortById(List<VirtualSensorVsnTo> virtualSensorVsnToList) {
        Comparator<VirtualSensorVsnTo> comparator = new Comparator<VirtualSensorVsnTo>() {
            @Override
            public int compare(VirtualSensorVsnTo o1, VirtualSensorVsnTo o2) {
                long id1 = o1.getId();
                long id2 = o2.getId();
                if(id1 < id2) {
                    return -1;
                } else if(id1 > id2) {
                    return 1;
                }
                return 0;
            }
        };
        Collections.sort(virtualSensorVsnToList, comparator);
    }

}
