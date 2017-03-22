package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.model.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.repository.CollectorRepository;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectorService {

    private CollectorRepository collectorRepository;
    private CollectorGenerator collectorGenerator;

    @Autowired
    public CollectorService(CollectorRepository collectorRepository, CollectorGenerator collectorGenerator) {
        this.collectorRepository = collectorRepository;
        this.collectorGenerator = collectorGenerator;
    }

    public CollectorCoTo getRandom() {
        return this.collectorGenerator.generate();
    }

    public List<CollectorCoTo> getAllByNetworkId(String networkId) {
        return this.collectorRepository.getAllByNetworkId(networkId);
    }

    public CollectorCoTo getByNetworkId(String networkId, String collectorId) {
        return this.collectorRepository.getByNetworkId(networkId, collectorId);
    }
}
