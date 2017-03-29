package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.mapper.CollectorMapper;
import br.uff.labtempo.osiris.model.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.repository.CollectorRepository;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;

import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectorService {

    private NetworkRepository networkRepository;
    private CollectorRepository collectorRepository;
    private CollectorGenerator collectorGenerator;

    @Autowired
    public CollectorService(NetworkRepository networkRepository, CollectorRepository collectorRepository, CollectorGenerator collectorGenerator) {
        this.networkRepository = networkRepository;
        this.collectorRepository = collectorRepository;
        this.collectorGenerator = collectorGenerator;
    }

    public CollectorResponse getRandom() {
        return CollectorMapper.toResponse(this.collectorGenerator.getCollectorCoTo());
    }

    public List<CollectorResponse> getAllByNetworkId(String networkId) {
        return CollectorMapper.toResponse(this.collectorRepository.getAllByNetworkId(networkId));
    }

    public CollectorResponse getByNetworkId(String networkId, String collectorId) {
        return CollectorMapper.toResponse(this.collectorRepository.getByNetworkId(networkId, collectorId));
    }

    public List<CollectorResponse> getAll() {
        List<CollectorResponse> collectorResponseList = new ArrayList<>();
        List<NetworkCoTo> networkCoToList = this.networkRepository.getAll();
        for(NetworkCoTo networkCoTo : networkCoToList) {
            collectorResponseList.addAll(CollectorMapper.toResponse(this.collectorRepository.getAllByNetworkId(networkCoTo.getId())));
        }
        return collectorResponseList;
    }
}
