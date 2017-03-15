package br.uff.labtempo.osiris.model.generator.sensor;

import br.uff.labtempo.osiris.model.domain.sensor.SensorConsumableRule;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.definitions.LogicalOperator;
import lombok.Data;

import java.util.*;

@Data
public class SensorConsumableRuleGenerator {

    private final int RANGE = 1;
    private Map<String, List<SensorConsumableRule>> sensorConsumableRules;

    public SensorConsumableRuleGenerator() {
        this.sensorConsumableRules = new LinkedHashMap<>();

        List<SensorConsumableRule> batteryRules = new ArrayList<>();
        batteryRules.add(SensorConsumableRule.builder().name("Low battery").consumableRule("battery")
        .logicalOperator(LogicalOperator.LESS_THAN).limitValue((int)(Math.random() * 61)).message("Battery is low.").build());
        batteryRules.add(SensorConsumableRule.builder().name("Full battery").consumableRule("battery")
                .logicalOperator(LogicalOperator.EQUAL).limitValue(100).message("Battery is full.").build());
        batteryRules.add(SensorConsumableRule.builder().name("Normal battery").consumableRule("battery")
                .logicalOperator(LogicalOperator.LESS_THAN).limitValue((int)(Math.random() * 15)).message("Battery is normal.").build());

        this.sensorConsumableRules.put("battery", batteryRules);

    }

    public Set<SensorConsumableRule> generate() {
        Set<SensorConsumableRule> consumableRules = new HashSet<>();
        List<SensorConsumableRule> rules = this.sensorConsumableRules.get("battery");
        consumableRules.add(rules.get((int) (Math.random() * rules.size())));
        return consumableRules;
    }
}
