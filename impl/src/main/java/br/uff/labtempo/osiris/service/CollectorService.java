package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.mapper.CollectorMapper;
import br.uff.labtempo.osiris.model.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
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

    public CollectorResponse getRandom() {
        return CollectorMapper.toResponse(this.collectorGenerator.generate());
    }

    public List<CollectorResponse> getAllByNetworkId(String networkId) {
        return CollectorMapper.toResponse(this.collectorRepository.getAllByNetworkId(networkId));
    }

    public CollectorResponse getByNetworkId(String networkId, String collectorId) {
        return CollectorMapper.toResponse(this.collectorRepository.getByNetworkId(networkId, collectorId));
    }
}
