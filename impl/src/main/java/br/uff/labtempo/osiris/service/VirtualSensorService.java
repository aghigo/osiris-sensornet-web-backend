package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.repository.VirtualSensorRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VirtualSensorService {

    @Autowired
    private VirtualSensorRepository virtualSensorRepository;

    public VirtualSensorVsnTo getById(String virtualSensorId) throws AbstractRequestException {
        return this.virtualSensorRepository.getById(virtualSensorId);
    }

    public List<VirtualSensorVsnTo> getAll() throws AbstractRequestException {
        return this.virtualSensorRepository.getAll();
    }
}
