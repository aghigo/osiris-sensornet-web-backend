package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.osiris.to.collector.CollectorCoTo;

import java.util.List;

public interface CollectorRepository {
    CollectorCoTo getByNetworkId(String networkId, String collectorId);
    List<CollectorCoTo> getAllByNetworkId(String networkId);
}
