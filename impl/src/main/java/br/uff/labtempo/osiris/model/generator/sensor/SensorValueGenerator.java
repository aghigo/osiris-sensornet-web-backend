package br.uff.labtempo.osiris.model.generator.sensor;


import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class SensorValueGenerator {

    private final int VALUE_RANGE = 99999;
    private List<SensorValue> sensorValues;

    public SensorValueGenerator(){
        this.sensorValues = new ArrayList<>();
        this.sensorValues.add(new SensorValue("temperature", randomLong(VALUE_RANGE), "celsius", "Cº"));
        this.sensorValues.add(new SensorValue("temperature", randomLong(VALUE_RANGE), "fahrenheit", "Fº"));
        this.sensorValues.add(new SensorValue("temperature", randomLong(VALUE_RANGE), "kelvin", "K"));
        this.sensorValues.add(new SensorValue("luminosity", randomLong(VALUE_RANGE), "candela", "C"));
        this.sensorValues.add(new SensorValue("pressure", randomLong(VALUE_RANGE), "pascal", "P"));
    }

    public Set<SensorValue> generate() {
        Set<SensorValue> sensorValues = new HashSet<>();
        for(int i = 0; i < (int) (Math.random() * randomSizeRange() + 1); i++) {
            sensorValues.add(this.sensorValues.get(randomSizeRange()));
        }
        return sensorValues;
    }

    private int randomSizeRange() {
        return (int) (Math.random() * (this.sensorValues.size()));
    }

    private long randomLong(int maxRange) {
        return (long) (Math.random() * (maxRange + 1));
    }
}
