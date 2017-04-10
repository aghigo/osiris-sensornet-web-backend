package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;

import java.util.List;

public interface VirtualSensorRepository {
    VirtualSensorVsnTo getById(String virtualSensorId) throws AbstractClientRuntimeException, AbstractRequestException;
    List<VirtualSensorVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException;
}
