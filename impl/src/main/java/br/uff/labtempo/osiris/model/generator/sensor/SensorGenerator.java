package br.uff.labtempo.osiris.model.generator.sensor;

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

    public SensorCoTo generate() {
        String id = getId();
        State state = State.NEW;
        long captureDateInMillis = new Date().getTime();
        int captureDateInNano = (int) TimeUnit.MILLISECONDS.convert(captureDateInMillis, TimeUnit.NANOSECONDS);
        long acquisitionDateInMillis = captureDateInMillis + randomLong(99999) + 1;

        SensorCoTo sensorCoTo = new SensorCoTo(id, state, captureDateInMillis, captureDateInNano, acquisitionDateInMillis);

        Set<SensorInfo> sensorInfo = this.sensorInfoGenerator.generate();
        for(SensorInfo i : sensorInfo) {
            sensorCoTo.addInfo(i.getInfoName(), i.getInfoDescription());
        }

        Set<SensorValue> sensorValue = this.sensorValueGenerator.generate();
        for(SensorValue s : sensorValue) {
            sensorCoTo.addValue(s.getName(), s.getValue(), s.getUnit(), s.getSymbol());
        }

        Set<List<SensorConsumableRule>> consumableRulesList = this.sensorConsumableRuleGenerator.generate();
        for(List<SensorConsumableRule> cl : consumableRulesList) {
            for(SensorConsumableRule c : cl){
                sensorCoTo.addConsumable(c.getName(), (int) randomLong(100));
                sensorCoTo.addConsumableRule(c.getName(), c.getConsumableName(), c.getOperator(), c.getLimitValue(), c.getMessage());
            }
        }

        return sensorCoTo;
    }

    private long randomLong(int maxRange) {
        return (long) (Math.random() * (maxRange + 1));
    }

    private String getId() {
        return "sensorId-" + UUID.randomUUID().toString();
    }
}
