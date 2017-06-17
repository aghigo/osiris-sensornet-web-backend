package br.uff.labtempo.osiris.schedule.mock;

import br.uff.labtempo.osiris.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.data.ConsumableRuleTo;
import br.uff.labtempo.osiris.to.common.data.ConsumableTo;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.to.common.definitions.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    private final long MAX_SENSORS = 1;
    private List<SampleCoTo> sampleCoToList = new ArrayList<>();
    private NetworkCoTo networkCoTo = new NetworkGenerator().getNetworkCoto();
    private CollectorCoTo collectorCoTo = new CollectorGenerator().getCollectorCoTo();

    @Autowired
    private SensorGenerator sensorGenerator;

    @Autowired
    private SampleRepository sampleRepository;

    private Random random = new Random();

    @Scheduled(cron="${virtualsensornet.schedule.mock.collector.cron:*/3 * * * * ?}")
    public void collectMockSensors() {
        if(this.sampleCoToList.size() < MAX_SENSORS) {
            SensorCoTo sensorCoTo = this.sensorGenerator.getSensorCoTo();
            SampleCoTo sampleCoTo = new SampleCoTo(this.networkCoTo, this.collectorCoTo, sensorCoTo);
            this.sampleCoToList.add(sampleCoTo);
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
        long nowInMillis = new Date().getTime();
        State sensorState = State.UPDATED;

        int capturePrecisionInNano = Math.toIntExact(this.collectorCoTo.getCaptureInterval()) / 2;
        long captureTimestampInMillis = nowInMillis;
        long acquisitionTimestampInMillis = nowInMillis + 1000;

        SensorCoTo newSensor = new SensorCoTo(currentSensor.getId(),
                sensorState, captureTimestampInMillis,
                capturePrecisionInNano, acquisitionTimestampInMillis);

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
            newSensor.addValue(valueTo.getName(), this.randomNumber(),
                    valueTo.getUnit(), valueTo.getSymbol());
        }
        return newSensor;
    }

    private long randomNumber() {
        return (long) Math.abs(this.random.nextInt(99999) + 1);
    }

}