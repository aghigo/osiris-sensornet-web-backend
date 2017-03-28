package br.uff.labtempo.osiris.schedule;

import br.uff.labtempo.osiris.model.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.model.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.model.generator.sample.SampleGenerator;
import br.uff.labtempo.osiris.model.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
@Profile("schedule")
@EnableScheduling
public class CreateSensorSchedule {

    private NetworkGenerator networkGenerator;
    private CollectorGenerator collectorGenerator;
    private SensorGenerator sensorGenerator;
    private SampleGenerator sampleGenerator;
    private SampleRepository sampleRepository;

    private SampleCoTo sampleCoTo;
    private static final Logger log = LoggerFactory.getLogger(CreateSensorSchedule.class);

    @Autowired
    public CreateSensorSchedule(NetworkGenerator networkGenerator,
                                CollectorGenerator collectorGenerator,
                                SensorGenerator sensorGenerator,
                                SampleGenerator sampleGenerator,
                                SampleRepository sampleRepository) {

        this.networkGenerator = networkGenerator;
        this.collectorGenerator = collectorGenerator;
        this.sensorGenerator = sensorGenerator;
        this.sampleGenerator = sampleGenerator;
        this.sampleRepository = sampleRepository;
    }

    @Scheduled(cron="${sensornet.schedule.create.sensor.cron:*/10 * * * * ?}")
    public void createNewSample() throws URISyntaxException {
        SampleCoTo sampleCoTo = this.sampleGenerator.generate();
        this.sampleRepository.save(sampleCoTo);
        log.info(String.format(String.format("SampleCoTo Created ([%s], [%s], [%s]).",
                sampleCoTo.getNetwork().getId(), sampleCoTo.getCollector().getId()
                ,sampleCoTo.getSensor().getId())));
    }

    @Scheduled(cron="${sensornet.schedule.create.collector.cron:*/10 * * * * ?}")
    public void createNewCollector() {
        CollectorCoTo collectorCoTo = this.collectorGenerator.generate();

        log.info(String.format("CollectorCoTo Created (%s)", collectorCoTo.getId()));
    }
//
//    @Scheduled(cron="${sensornet.schedule.create.sensor.cron:*/10 * * * * ?}")
//    public void createNewSensor() {
//        SensorCoTo sensorCoTo = this.sensorGenerator.generate();
//        log.info(String.format("Sensor Created (%s)", sensorCoTo.getId()));
//    }
}
