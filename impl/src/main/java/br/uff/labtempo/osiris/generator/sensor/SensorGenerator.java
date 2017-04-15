package br.uff.labtempo.osiris.generator.sensor;

import br.uff.labtempo.osiris.model.domain.sensor.SensorConsumableRule;
import br.uff.labtempo.osiris.model.domain.sensor.SensorInfo;
import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.definitions.State;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Class responsible for generate random Sensor mock objects for SensorNet module
 * @see SensorCoTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
public class SensorGenerator {
    private SensorInfoGenerator sensorInfoGenerator;
    private SensorValueGenerator sensorValueGenerator;
    private SensorConsumableRuleGenerator sensorConsumableRuleGenerator;

    public SensorGenerator() {
        this.sensorInfoGenerator = new SensorInfoGenerator();
        this.sensorValueGenerator = new SensorValueGenerator();
        this.sensorConsumableRuleGenerator = new SensorConsumableRuleGenerator();
    }

    /**
     * Generate a random SensorCoTo mock object
     * @see SensorCoTo
     * @return SensorCoTo
     */
    public SensorCoTo getSensorCoTo() {
        String id = getId();
        State state = State.NEW;
        long captureDateInMillis = new Date().getTime();
        int captureDateInNano = (int) TimeUnit.MILLISECONDS.convert(captureDateInMillis, TimeUnit.NANOSECONDS);
        long acquisitionDateInMillis = captureDateInMillis + randomLong(99999) + 1;

        SensorCoTo sensorCoTo = new SensorCoTo(id, state, captureDateInMillis, captureDateInNano, acquisitionDateInMillis);

        Set<SensorInfo> sensorInfo = this.sensorInfoGenerator.getSensorInfoSet();
        for(SensorInfo i : sensorInfo) {
            sensorCoTo.addInfo(i.getInfoName(), i.getInfoDescription());
        }

        Set<SensorValue> sensorValue = this.sensorValueGenerator.getSensorValueSet();
        for(SensorValue s : sensorValue) {
            sensorCoTo.addValue(s.getName(), s.getValue(), s.getUnit(), s.getSymbol());
        }

        Set<List<SensorConsumableRule>> consumableRulesList = this.sensorConsumableRuleGenerator.getSensorConsumableRuleListSet();
        for(List<SensorConsumableRule> cl : consumableRulesList) {
            for(SensorConsumableRule c : cl){
                sensorCoTo.addConsumable(c.getName(), (int) randomLong(100));
                sensorCoTo.addConsumableRule(c.getName(), c.getConsumableName(), c.getOperator(), c.getLimitValue(), c.getMessage());
            }
        }
        return sensorCoTo;
    }

    /**
     * Generate a random Long value from 0 to maxRange
     * @param maxRange
     * @return long value from 0 to maxRange
     */
    private long randomLong(int maxRange) {
        return (long) (Math.random() * (maxRange + 1));
    }

    /**
     * Generate a random unique Sensor id
     * Pattern: "sensorId-" + UUID
     * @see UUID
     * @return String with the sensor id
     */
    private String getId() {
        return "sensorId-" + UUID.randomUUID().toString();
    }
}
