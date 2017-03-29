package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;

import java.util.List;

public interface CollectorRepository {
    CollectorSnTo getByNetworkId(String networkId, String collectorId);
    List<CollectorSnTo> getAllByNetworkId(String networkId);
}
