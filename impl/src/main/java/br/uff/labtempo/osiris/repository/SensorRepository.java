package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;

import java.util.List;

public interface SensorRepository {
    SensorSnTo getByCollectorIdAndNetworkId(String networkId, String collectorId, String sensorId);
    List<SensorSnTo> getAllByCollectorIdAndNetworkId(String networkId, String collectorId);
    List<SensorSnTo> getAllByNetworkId(String networkId);
}
