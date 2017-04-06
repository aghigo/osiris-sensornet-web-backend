package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;

import java.util.List;

public interface NetworkRepository {
    NetworkSnTo getById(String id) throws AbstractRequestException, AbstractClientRuntimeException;
    List<NetworkSnTo> getAll() throws AbstractRequestException, AbstractClientRuntimeException;
}
