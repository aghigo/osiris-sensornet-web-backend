package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.mapper.SensorMapper;
import br.uff.labtempo.osiris.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.model.response.SensorResponse;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class with business rules to select/create/update/remove Sensors from/on SensorNet module
 * @see SensorCoTo
 * @see br.uff.labtempo.osiris.to.sensornet.SensorSnTo
 * @see br.uff.labtempo.osiris.model.request.SensorRequest
 * @see SensorResponse
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Service
public class SensorService {

    private NetworkRepository networkRepository;
    private SensorRepository sensorRepository;
    private SensorGenerator sensorGenerator;

    @Autowired
    public SensorService(NetworkRepository networkRepository, SensorRepository sensorRepository, SensorGenerator sensorGenerator) {
        this.networkRepository = networkRepository;
        this.sensorRepository = sensorRepository;
        this.sensorGenerator = sensorGenerator;
    }

    /**
     * Creates a random Sensor mocked object
     * @see SensorCoTo
     * @return SensorCoTo
     */
    public SensorCoTo getRandom() {
        return this.sensorGenerator.getSensorCoTo();
    }

    /**
     * Get a specific Sensor from SensorNet module based on given networkId, collectorId and sensorId
     * @param networkId
     * @param collectorId
     * @param sensorId
     * @return SensorResponse (sensor with {networkId} from {collectorId} Collector from {networkId} Network)
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public SensorResponse getByCollectorIdAndNetworkId(String networkId, String collectorId, String sensorId) throws AbstractRequestException, AbstractClientRuntimeException {
        return SensorMapper.snToToResponse(this.sensorRepository.getByCollectorIdAndNetworkId(networkId, collectorId, sensorId));
    }

    /**
     * Get all Sensors from a Collector from a Network from SensorNet module
     * @param networkId
     * @param collectorId
     * @return SensorResponse
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public List<SensorResponse> getAllByCollectorIdAndNetworkId(String networkId, String collectorId) throws AbstractRequestException, AbstractClientRuntimeException {
        return SensorMapper.snToToResponse(this.sensorRepository.getAllByCollectorIdAndNetworkId(networkId, collectorId));
    }

    /**
     * Get all Sensors from a Network from SensorNet
     * @param networkId
     * @return List of SensorResponse
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public List<SensorResponse> getAllByNetworkId(String networkId) throws AbstractRequestException, AbstractClientRuntimeException {
        return SensorMapper.snToToResponse(this.sensorRepository.getAllByNetworkId(networkId));
    }

    /**
     * Get all sensors from SensorNet module
     * @return List of SensorResponse
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public List<SensorResponse> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        List<SensorResponse> sensorResponseList = new ArrayList<>();
        List<NetworkSnTo> networkCoToList = this.networkRepository.getAll();
        for(NetworkSnTo networkSnTo : networkCoToList) {
            sensorResponseList.addAll(SensorMapper.snToToResponse(this.sensorRepository.getAllByNetworkId(networkSnTo.getId())));
        }
        return sensorResponseList;
    }
}
