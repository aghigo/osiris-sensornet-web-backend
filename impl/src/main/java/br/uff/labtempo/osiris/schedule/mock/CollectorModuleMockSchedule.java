package br.uff.labtempo.osiris.schedule.mock;

import br.uff.labtempo.osiris.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.model.domain.sensor.Sensor;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.data.ConsumableRuleTo;
import br.uff.labtempo.osiris.to.common.data.ConsumableTo;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.utils.announcement.Announcement;
import br.uff.labtempo.osiris.utils.announcement.core.AnnouncementManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulates a Collector module with Mock Sensors, Collectors and Networks
 * @author andre.ghigo
 * @version 1.0.0
 * @since 13/06/17.
 */
@Slf4j
@Service
@Profile("collector_mock_schedule")
@EnableScheduling
public class CollectorModuleMockSchedule {
    private final long MAX_SENSORS = 50;
    private List<SampleCoTo> sampleCoToList = new ArrayList<>();
    private NetworkCoTo networkCoTo = new NetworkGenerator().getNetworkCoto();
    private CollectorCoTo collectorCoTo = new CollectorGenerator().getCollectorCoTo();
    private long generatedId = 0;

    @Autowired
    private SensorGenerator sensorGenerator;

    @Autowired
    private SampleRepository sampleRepository;

    @Scheduled(cron="${virtualsensornet.schedule.mock.link.cron:*/2 * * * * ?}")
    public void collectMockSensors() {
        if(this.sampleCoToList == null) {
            this.sampleCoToList = new ArrayList<>();
        }
        if(this.sampleCoToList.size() < MAX_SENSORS) {
            SensorCoTo sensorCoTo = this.sensorGenerator.getSensorCoTo();
            SampleCoTo sampleCoTo = new SampleCoTo(this.networkCoTo, this.collectorCoTo, sensorCoTo);
            this.sampleCoToList.add(sampleCoTo);
        }
        if(this.sampleCoToList.size() == 1) {
            return;
        }
        for(int i = 0; i < this.sampleCoToList.size(); i++) {
            SampleCoTo sample = this.sampleCoToList.get(i);
            SampleCoTo updatedSample = new SampleCoTo(networkCoTo, collectorCoTo, this.cloneWithDifferentValues(sample.getSensor()));
            this.sampleCoToList.remove(i);
            this.sampleCoToList.add(updatedSample);
            this.sampleRepository.save(updatedSample);
            log.info(String.format("Sensor %s collected.", updatedSample.getSensor().getId()));
        }
    }

    private SensorCoTo cloneWithDifferentValues(SensorCoTo currentSensor) {
        SensorCoTo newSensor = new SensorCoTo(currentSensor.getId(),
                currentSensor.getState(), currentSensor.getCaptureTimestampInMillis(),
                currentSensor.getCapturePrecisionInNano(), currentSensor.getAcquisitionTimestampInMillis());

        newSensor.addInfo(currentSensor.getInfo());

        for(ConsumableRuleTo consumableRuleTo : currentSensor.getConsumableRulesTo()) {
            newSensor.addConsumableRule(consumableRuleTo.getName(),
                    consumableRuleTo.getConsumableName(), consumableRuleTo.getOperator(),
                    consumableRuleTo.getLimitValue(), consumableRuleTo.getMessage());
        }
        for(ConsumableTo consumableTo : currentSensor.getConsumablesTo()) {
            newSensor.addConsumable(consumableTo.getName(), consumableTo.getValue());
        }

        for(ValueTo valueTo : currentSensor.getValuesTo()) {
            newSensor.addValue(valueTo.getName(), this.getRandomValue(),
                    valueTo.getUnit(), valueTo.getSymbol());
        }
        return newSensor;
    }

    private long getRandomValue() {
        return (long) (Math.random() * 99999) + 1;
    }
}