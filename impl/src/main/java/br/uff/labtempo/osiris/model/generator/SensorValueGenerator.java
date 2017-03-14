package br.uff.labtempo.osiris.model.generator;


import br.uff.labtempo.osiris.model.domain.SensorValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SensorValueGenerator {
    private List<SensorValue> sensorValues;

    private SensorValueGenerator(){
        this.sensorValues = new ArrayList<>();
        this.sensorValues.add(new SensorValue("temperature", (long) (Math.random() * 9999), "celsius", "Cº"));
        this.sensorValues.add(new SensorValue("temperature", (long) (Math.random() * 9999), "fahrenheit", "Fº"));
        this.sensorValues.add(new SensorValue("temperature", (long) (Math.random() * 9999), "kelvin", "K"));
        this.sensorValues.add(new SensorValue("luminosity", (long) (Math.random() * 9999), "candela", "C"));
        this.sensorValues.add(new SensorValue("pressure", (long) (Math.random() * 9999), "pascal", "P"));
    }

    public SensorValue generate() {
        return this.sensorValues.get((int) (Math.random() * this.sensorValues.size()));
    }
}
