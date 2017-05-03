package br.uff.labtempo.osiris.schedule;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.generator.sample.SampleGenerator;
import br.uff.labtempo.osiris.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.mapper.CollectorMapper;
import br.uff.labtempo.osiris.mapper.NetworkMapper;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.service.CollectorService;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
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
 * Creates periodically new mocked SampleCoTo, NetworkCoTo, CollectorCoTo and SensorCoTo objects on SensorNet module
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
    private NetworkRepository networkRepository;

//    @Scheduled(cron="${sensornet.schedule.create.sample.cron:*/10 * * * * ?}")
//    public void createNewSample() throws URISyntaxException {
//        SampleCoTo sampleCoTo = this.sampleGenerator.getSampleCoTo();
//        this.sampleRepository.save(sampleCoTo);
//        log.info(String.format(String.format("SampleCoTo Created ([%s], [%s], [%s]).", sampleCoTo.getNetwork().getId(), sampleCoTo.getCollector().getId(), sampleCoTo.getSensor().getId())));
//    }

//    @Scheduled(cron="${sensornet.schedule.create.networkAndSensor.cron:*/10 * * * * ?}")
//    public void createNewNetworkAndNewSensorOnExistingCollector() throws AbstractRequestException {
//        NetworkCoTo networkCoTo = this.networkGenerator.getNetworkCoto();
//        SensorCoTo sensorCoTo = this.sensorGenerator.getSensorCoTo();
//        List<CollectorResponse> collectorResponseList = this.collectorService.getAll();
//        int p = (int) (Math.random() * collectorResponseList.size());
//        CollectorCoTo collectorCoTo = CollectorMapper.responseToCoTo(collectorResponseList.get(p));
//        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
//        this.sampleRepository.save(sampleCoTo);
//        log.info(String.format("New Network [%s] and new Sensor [%s] created from existing Collector [%s]", networkCoTo.getId(), sensorCoTo.getId(), collectorCoTo.getId()));
//    }
//
//    @Scheduled(cron="${sensornet.schedule.create.sensor.cron:*/10 * * * * ?}")
//    public void createNewSensorOnExistingCollectorAndNetwork() throws AbstractRequestException {
//        List<NetworkSnTo> networkSnToList = this.networkRepository.getAll();
//        int networkPos = (int) (Math.random() * networkSnToList.size());
//        NetworkCoTo networkCoTo = NetworkMapper.snToToCoTo(networkSnToList.get(networkPos));
//        List<CollectorResponse> collectorResponseList = this.collectorService.getAll();
//        int collectorPos = (int) (Math.random() * collectorResponseList.size());
//        CollectorCoTo collectorCoTo = CollectorMapper.responseToCoTo(collectorResponseList.get(collectorPos));
//        SensorCoTo sensorCoTo = this.sensorGenerator.getSensorCoTo();
//        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
//        this.sampleRepository.save(sampleCoTo);
//        log.info(String.format("New Sensor [%] created on existing Collector [%s] and existing Network [%s]", sensorCoTo.getId(), collectorCoTo.getId(), networkCoTo.getId()));
//    }
}
