package br.uff.labtempo.osiris.model.generator.sensor;

import br.uff.labtempo.osiris.model.domain.SensorConsumableRule;
import br.uff.labtempo.osiris.model.domain.SensorInfo;
import br.uff.labtempo.osiris.model.domain.SensorValue;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.definitions.State;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class SensorGenerator {

    private final int INFO_RANGE = 5;
    private final int VALUE_RANGE = 4;
    private final int ID_RANGE = 999999;

    private SensorInfoGenerator sensorInfoGenerator;
    private SensorValueGenerator sensorValueGenerator;
    private SensorConsumableRuleGenerator sensorConsumableRuleGenerator;

    public SensorGenerator() {
        this.sensorInfoGenerator = new SensorInfoGenerator();
        this.sensorValueGenerator = new SensorValueGenerator();
        this.sensorConsumableRuleGenerator = new SensorConsumableRuleGenerator();
    }

    public SensorCoTo generate() {
        String id = "sensorId" + randomLong(ID_RANGE);
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
                sensorCoTo.addConsumableRule(c.getName(), c.getConsumableRule(), c.getLogicalOperator(), c.getLimitValue(), c.getMessage());
            }
        }

        return sensorCoTo;
    }

    private State getState() {
        return State.values()[(int) (Math.random() * State.values().length)];
    }

    private long randomLong(int maxRange) {
        return (long) (Math.random() * (maxRange + 1));
    }
}
