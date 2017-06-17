package br.uff.labtempo.osiris.schedule.mock;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.mapper.CollectorMapper;
import br.uff.labtempo.osiris.mapper.NetworkMapper;
import br.uff.labtempo.osiris.mapper.SensorMapper;
import br.uff.labtempo.osiris.model.response.SensorResponse;
import br.uff.labtempo.osiris.repository.CollectorRepository;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.service.SensorService;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.data.ConsumableRuleTo;
import br.uff.labtempo.osiris.to.common.data.ConsumableTo;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.to.common.definitions.State;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;
import javassist.tools.rmi.Sample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    private final long MAX_SENSORS = 5;
    private List<SampleCoTo> sampleCoToList = new ArrayList<>();
    private NetworkCoTo networkCoTo;
    private CollectorCoTo collectorCoTo;

    @Autowired
    private SensorGenerator sensorGenerator;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private CollectorRepository collectorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    private Random random = new Random();

    @PostConstruct
    public void initializeCollectorModuleMock() throws AbstractRequestException {
        List<NetworkSnTo> networkSnToList = this.networkRepository.getAll();
        if(networkSnToList.isEmpty()) {
            this.networkCoTo = new NetworkGenerator().getNetworkCoto();
        } else {
            this.networkCoTo = NetworkMapper.snToToCoTo(networkSnToList.get(0));
        }
        List<CollectorSnTo> collectorSnToList = this.collectorRepository.getAllByNetworkId(this.networkCoTo.getId());
        if(collectorSnToList.isEmpty()) {
            this.collectorCoTo = new CollectorGenerator().getCollectorCoTo();
        } else {
            this.collectorCoTo = CollectorMapper.snToToCoTo(collectorSnToList.get(0));
        }
        List<SensorCoTo> sensorCoToList = SensorMapper.snToToCoTo(this.sensorRepository.getAllByCollectorIdAndNetworkId(this.networkCoTo.getId(), this.collectorCoTo.getId()));
        for(int i = 0; i < this.MAX_SENSORS && i < sensorCoToList.size(); i++) {
            SampleCoTo sampleCoTo = new SampleCoTo(this.networkCoTo, this.collectorCoTo, sensorCoToList.get(i));
            this.sampleRepository.update(sampleCoTo);
            this.sampleCoToList.add(sampleCoTo);
        }
        for(int i = sampleCoToList.size(); i < this.MAX_SENSORS; i++) {
            SensorCoTo sensorCoTo = this.sensorGenerator.getSensorCoTo();
            SampleCoTo sampleCoTo = new SampleCoTo(this.networkCoTo, this.collectorCoTo, sensorCoTo);
            this.sampleRepository.save(sampleCoTo);
            this.sampleCoToList.add(sampleCoTo);
        }
    }

    @Scheduled(cron="${virtualsensornet.schedule.mock.collector.cron:*/1 * * * * ?}")
    public void collectMockSensors() throws AbstractRequestException {
        for(int i = 0; i < this.sampleCoToList.size(); i++) {
            SampleCoTo sample = this.sampleCoToList.get(i);
            SampleCoTo updatedSample = new SampleCoTo(networkCoTo, collectorCoTo, this.cloneWithDifferentValues(sample.getSensor()));
            this.sampleCoToList.remove(i);
            this.sampleCoToList.add(updatedSample);
            this.sampleRepository.save(updatedSample);
            log.info(String.format("Sensor %s collected.", updatedSample.getSensor().getId()));
        }
    }

    private List<SensorCoTo> getExistingSensors() throws AbstractRequestException {
        List<SensorCoTo> sensorCoToList = SensorMapper.snToToCoTo(this.sensorRepository.getAllByCollectorIdAndNetworkId(this.networkCoTo.getId(), this.collectorCoTo.getId()));
        return sensorCoToList;
    }

    private SensorCoTo cloneWithDifferentValues(SensorCoTo currentSensor) {
        long nowInMillis = new Date().getTime();
        State sensorState = State.UPDATED;

        int capturePrecisionInNano = 0;

        long captureTimestampInMillis = currentSensor.getCaptureTimestampInMillis() + (TimeUnit.MILLISECONDS.convert(this.collectorCoTo.getCaptureInterval(), this.collectorCoTo.getCaptureIntervalTimeUnit()) / 2);
        long acquisitionTimestampInMillis = captureTimestampInMillis + this.random.nextInt(capturePrecisionInNano + 1);

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
        return (long) Math.abs(this.random.nextInt(9999) + 1);
    }

}