package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.mapper.CollectorMapper;
import br.uff.labtempo.osiris.mapper.NetworkMapper;
import br.uff.labtempo.osiris.mapper.SampleMapper;
import br.uff.labtempo.osiris.mapper.SensorMapper;
import br.uff.labtempo.osiris.generator.sample.SampleGenerator;
import br.uff.labtempo.osiris.model.request.SampleRequest;
import br.uff.labtempo.osiris.model.response.SampleResponse;
import br.uff.labtempo.osiris.repository.CollectorRepository;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Service class with business rules to select/create/update/remove Samples from/on SensorNet module
 * @see SampleRequest
 * @see SampleCoTo
 * @see SampleResponse
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Service
@PropertySource(value = "classpath:application.properties")
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private CollectorRepository collectorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SampleGenerator sampleGenerator;

    /**
     * Get a random Sample mocked object
     * @see SampleCoTo
     * @return SampleResponse
     */
    public SampleResponse getRandom() {
        SampleCoTo sampleCoTo = this.sampleGenerator.getSampleCoTo();
        SampleResponse sampleResponse = SampleResponse.builder()
                .network(sampleCoTo.getNetwork())
                .collector(sampleCoTo.getCollector())
                .sensor(sampleCoTo.getSensor())
                .build();
        return sampleResponse;
    }

    /**
     * Creates a new Sample on SensorNet module
     * @param sampleRequest
     * @return URI with the new Sample Location
     * @throws URISyntaxException
     */
    public URI create(SampleRequest sampleRequest) throws URISyntaxException {
        SampleCoTo sampleCoTo = SampleMapper.requestToCoTo(sampleRequest);
        this.sampleRepository.save(sampleCoTo);

        URI uri = new URI(String.format("/sensornet/network/%s/collector/%s/sensor/%s/", sampleCoTo.getNetwork().getId(),
                sampleCoTo.getCollector().getId(), sampleCoTo.getSensor().getId()));

        return uri;
    }

    /**
     * Updates a Sample on update messagegroup
     * @param sampleCoTo
     */
    public void update(SampleCoTo sampleCoTo) {
        this.sampleRepository.update(sampleCoTo);
    }

    /**
     * Get a Sample from SensorNet module by given a valid (networkId + collectorId + sensorId) association
     * @param sensorId
     * @param collectorId
     * @param networkId
         * @return SampleResponse
     * @throws AbstractRequestException
     */
    public SampleResponse getSample(String sensorId, String collectorId, String networkId) throws AbstractRequestException {
        NetworkCoTo networkCoTo = NetworkMapper.snToToCoTo(this.networkRepository.getById(networkId));
        CollectorCoTo collectorCoTo = CollectorMapper.snToToCoTo(this.collectorRepository.getByNetworkId(networkId, collectorId));
        SensorCoTo sensorCoTo = SensorMapper.snToToCoTo(this.sensorRepository.getByCollectorIdAndNetworkId(networkId, collectorId, sensorId));
        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
        SampleResponse sampleResponse = SampleMapper.coToToResponse(sampleCoTo);
        return sampleResponse;
    }
}
