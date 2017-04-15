package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.repository.VirtualSensorRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class with business rules to select/create/update/remove VirtualSensors (vsensors) from the VirtualSensorNet module
 * @see VirtualSensorVsnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Service
public class VirtualSensorService {

    @Autowired
    private VirtualSensorRepository virtualSensorRepository;
    /**
     * Get a VirtualSensor (vsensor) from VirtualSensorNet by its unique Id
     * @see VirtualSensorVsnTo
     * @param virtualSensorId
     * @return VirtualSensorVsnTo
     * @throws AbstractRequestException
     */
    public VirtualSensorVsnTo getById(String virtualSensorId) throws AbstractRequestException {
        return this.virtualSensorRepository.getById(virtualSensorId);
    }

    /**
     * Get all VirtualSensors (vsensors) from VirtualSensorNet module
     * @see VirtualSensorVsnTo
     * @return List of VirtualSensorVsnTo
     * @throws AbstractRequestException
     */
    public List<VirtualSensorVsnTo> getAll() throws AbstractRequestException {
        return this.virtualSensorRepository.getAll();
    }
}
