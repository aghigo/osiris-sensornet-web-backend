package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;

import java.util.List;

/**
 * Interface to abstract Sensor DAO implementation
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public interface SensorRepository {
    SensorSnTo getByCollectorIdAndNetworkId(String networkId, String collectorId, String sensorId) throws AbstractRequestException, AbstractClientRuntimeException;
    List<SensorSnTo> getAllByCollectorIdAndNetworkId(String networkId, String collectorId) throws AbstractRequestException, AbstractClientRuntimeException;
    List<SensorSnTo> getAllByNetworkId(String networkId) throws AbstractRequestException, AbstractClientRuntimeException;
}
