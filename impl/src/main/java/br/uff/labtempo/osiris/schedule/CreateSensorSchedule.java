package br.uff.labtempo.osiris.schedule;

import br.uff.labtempo.osiris.service.CollectorService;
import br.uff.labtempo.osiris.service.NetworkService;
import br.uff.labtempo.osiris.service.SampleService;
import br.uff.labtempo.osiris.service.SensorService;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Slf4j
@Service
@EnableScheduling
public class CreateSensorSchedule {

    private SampleService sampleService;
    private NetworkService networkService;
    private CollectorService collectorService;
    private SensorService sensorService;

    @Autowired
    public CreateSensorSchedule(SampleService sampleService, NetworkService networkService,
                                CollectorService collectorService, SensorService sensorService) {
        this.sampleService = sampleService;
        this.networkService = networkService;
        this.collectorService = collectorService;
        this.sensorService = sensorService;
    }

//    @Scheduled(cron="${sensornet.schedule.create.sensor.cron:*/10 * * * * ?}")
//    public void createNewSampleCoTo() throws URISyntaxException {
//
//    }
}
