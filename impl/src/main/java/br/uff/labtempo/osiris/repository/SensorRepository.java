package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.osiris.to.collector.SensorCoTo;

import java.util.List;

public interface SensorRepository {
    SensorCoTo getByCollectorIdAndNetworkId(String networkId, String collectorId, String sensorId);
    List<SensorCoTo> getAllByCollectorIdAndNetworkId(String networkId, String collectorId);
    List<SensorCoTo> getAllByNetworkId(String networkId);
}
