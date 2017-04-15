package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.mapper.CollectorMapper;
import br.uff.labtempo.osiris.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.repository.CollectorRepository;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;

import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service with business rules to get/create/update/remove Collectors from/on  SensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
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

    /**
     * Get a random mock of the Collector sensor
     * @see CollectorCoTo
     * @return CollectorCoTo
     */
    public CollectorCoTo getRandom() {
        return this.collectorGenerator.getCollectorCoTo();
    }


    /**
     * Get a list of all Collectors from a given {networkId} Network from SensorNet module
     * @param networkId
     * @return List of CollectorResponse
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public List<CollectorResponse> getAllByNetworkId(String networkId) throws AbstractRequestException, AbstractClientRuntimeException {
        return CollectorMapper.snToToResponse(this.collectorRepository.getAllByNetworkId(networkId));
    }

    /**
     * Get a specific Collector from SensorNet module based on {networkId} and {collectorId}
     * @param networkId
     * @param collectorId
     * @return CollectorResponse with collectorId
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public CollectorResponse getByNetworkId(String networkId, String collectorId) throws AbstractRequestException, AbstractClientRuntimeException {
        return CollectorMapper.snToToResponse(this.collectorRepository.getByNetworkId(networkId, collectorId));
    }

    /**
     * Get a list of all available Collectors from SensorNet module
     * @return List of CollectorResponse
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public List<CollectorResponse> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        List<CollectorResponse> collectorResponseList = new ArrayList<>();
        List<NetworkSnTo> networkCoToList = this.networkRepository.getAll();
        for(NetworkSnTo networkSnTo : networkCoToList) {
            collectorResponseList.addAll(CollectorMapper.snToToResponse(this.collectorRepository.getAllByNetworkId(networkSnTo.getId())));
        }
        return collectorResponseList;
    }
}
