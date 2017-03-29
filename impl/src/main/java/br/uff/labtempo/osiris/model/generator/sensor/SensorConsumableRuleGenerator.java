package br.uff.labtempo.osiris.model.generator.sensor;

import br.uff.labtempo.osiris.model.domain.sensor.SensorConsumableRule;
import br.uff.labtempo.osiris.to.common.definitions.LogicalOperator;
import lombok.Data;

import java.util.*;

@Data
public class SensorConsumableRuleGenerator {

    private Map<String, List<SensorConsumableRule>> sensorConsumableRules;

    public SensorConsumableRuleGenerator() {
        this.sensorConsumableRules = new LinkedHashMap<>();

        List<SensorConsumableRule> batteryRules = new ArrayList<>();

        batteryRules.add(SensorConsumableRule.builder().name("Low battery").consumableName("battery")
        .operator(LogicalOperator.LESS_THAN).limitValue((int)(Math.random() * 61)).message("Battery is low.").build());

        batteryRules.add(SensorConsumableRule.builder().name("Full battery").consumableName("battery")
                .operator(LogicalOperator.EQUAL).limitValue(100).message("Battery is full.").build());

        batteryRules.add(SensorConsumableRule.builder().name("Normal battery").consumableName("battery")
                .operator(LogicalOperator.LESS_THAN).limitValue((int)(Math.random() * 15)).message("Battery is normal.").build());

        this.sensorConsumableRules.put("battery", batteryRules);

        List<SensorConsumableRule> signalRules = new ArrayList<>();

        signalRules.add(SensorConsumableRule.builder().name("Low Signal").consumableName("signal")
        .operator(LogicalOperator.LESS_THAN).limitValue((int)(Math.random() * 15))
        .message("Signal is low.").build());

        signalRules.add(SensorConsumableRule.builder().name("Normal Signal").consumableName("signal")
                .operator(LogicalOperator.GREATER_THAN).limitValue((int)(Math.random() * 61))
                .message("Signal is normal.").build());

        signalRules.add(SensorConsumableRule.builder().name("Good Signal").consumableName("signal")
                .operator(LogicalOperator.EQUAL).limitValue(100)
                .message("Signal is good.").build());

        this.sensorConsumableRules.put("signal", signalRules);

    }

    public Set<List<SensorConsumableRule>> getSensorConsumableRuleListSet() {
        Set<List<SensorConsumableRule>> consumableRules = new LinkedHashSet<>();
        for(int i = 0; i < (int) (Math.random() * (this.sensorConsumableRules.size()) + 1); i++) {
            consumableRules.add(random());
        }
        return consumableRules;
    }

    private List<SensorConsumableRule> random() {
        int positon = (int) (Math.random() * this.sensorConsumableRules.keySet().toArray().length);
        String key = (String) this.sensorConsumableRules.keySet().toArray()[positon];
        return this.sensorConsumableRules.get(key);
    }
}
