package br.uff.labtempo.osiris.schedule.mock;

import br.uff.labtempo.osiris.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.generator.sample.SampleGenerator;
import br.uff.labtempo.osiris.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.mapper.CollectorMapper;
import br.uff.labtempo.osiris.mapper.NetworkMapper;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.repository.CollectorRepository;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.service.CollectorService;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Schedule Jobs to mock Collector
 * Creates periodically new randomly mocked SampleCoTo, NetworkCoTo, CollectorCoTo and SensorCoTo objects on SensorNet module
 */
@Service
@Profile("sensornet_mock_schedule")
@EnableScheduling
public class SensorNetMockSchedule {
    private static final Logger log = LoggerFactory.getLogger(SensorNetMockSchedule.class);

    @Autowired
    private NetworkGenerator networkGenerator;

    @Autowired
    private SensorGenerator sensorGenerator;

    @Autowired
    private SampleGenerator sampleGenerator;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private CollectorService collectorService;

    @Autowired
    private CollectorRepository collectorRepository;

    @Autowired
    private NetworkRepository networkRepository;

    /**
     * Creates ammount of mocked data (sensor, collector, network) on SensorNet module
     */
    @Scheduled(fixedDelay = 999999999)
    public void loadInitialSensorNetMockedData() throws Exception {
        log.info("initializing SensorNet database with mocked Sensors, Collectors and Networks...");
        for(int i = 0; i < ((int) Math.random() * 90) + 1; i++) {
            this.createNewSample();
        }
        for(int i = 0; i < ((int) Math.random() * 90) + 1; i++) {
            this.createNewNetworkAndNewSensorOnExistingCollector();
        }
        for(int i = 0; i < ((int) Math.random() * 90) + 1; i++) {
            this.createNewSensorOnExistingCollectorAndNetwork();
        }
    }

    /**
     * Periodically creates a new mocked sample (network + collector + sensor) object periodically onto SensorNet module
     * @throws URISyntaxException
     */
    private void createNewSample() throws URISyntaxException {
        SampleCoTo sampleCoTo = this.sampleGenerator.getSampleCoTo();
        this.sampleRepository.save(sampleCoTo);
        log.info(String.format(String.format("SampleCoTo Created (network [%s], collector [%s], sensor [%s]).", sampleCoTo.getNetwork().getId(), sampleCoTo.getCollector().getId(), sampleCoTo.getSensor().getId())));
    }

    /**
     * Periodically creates a new mocked Network and Sensor objects associated to existing Collector onto SensorNet module
     * @throws Exception
     */
    private void createNewNetworkAndNewSensorOnExistingCollector() throws Exception {
        NetworkCoTo networkCoTo = this.networkGenerator.getNetworkCoto();
        SensorCoTo sensorCoTo = this.sensorGenerator.getSensorCoTo();
        List<CollectorResponse> collectorResponseList = this.collectorService.getAll();
        if(collectorResponseList == null || collectorResponseList.isEmpty()) {
            throw new Exception("Failed to create Network and Sensor: No Collector found.");
        }
        int p = (int) (Math.random() * collectorResponseList.size());
        CollectorCoTo collectorCoTo = CollectorMapper.responseToCoTo(collectorResponseList.get(p));
        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
        this.sampleRepository.save(sampleCoTo);
        log.info(String.format("New Network [%s] and new Sensor [%s] created from existing Collector [%s]", networkCoTo.getId(), sensorCoTo.getId(), collectorCoTo.getId()));
    }

    /**
     * Periodically creates a new mocked Sensor object from associated to existing Collector and Network onto SensorNet module
     * @throws Exception
     */
    private void createNewSensorOnExistingCollectorAndNetwork() throws Exception {
        List<NetworkSnTo> networkSnToList = this.networkRepository.getAll();
        if(networkSnToList == null || networkSnToList.isEmpty()) {
            throw new Exception("Failed to create new Sensor: No networks found.");
        }
        int p = (int) (Math.random() * networkSnToList.size());
        NetworkCoTo networkCoTo = NetworkMapper.snToToCoTo(networkSnToList.get(p));
        List<CollectorSnTo> collectorSnToList = this.collectorRepository.getAllByNetworkId(networkCoTo.getId());
        if(collectorSnToList == null || collectorSnToList.isEmpty()) {
            throw new Exception("Failed to create new Sensor: No collectors found.");
        }
        p = (int) (Math.random() * collectorSnToList.size());
        CollectorCoTo collectorCoTo = CollectorMapper.snToToCoTo(collectorSnToList.get(p));
        SensorCoTo sensorCoTo = this.sensorGenerator.getSensorCoTo();
        this.sampleRepository.save(new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo));
        log.info(String.format("New Sensor [%s] created from existing Collector [%s] and from Network [%s]", sensorCoTo.getId(), collectorCoTo.getId(), networkCoTo.getId()));
    }
}
