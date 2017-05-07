package br.uff.labtempo.osiris.generator.sensor;


import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Complementary class for SensorGenerator
 * to generate random Sensor Values
 * a Sensor Value is a object for sensor measures
 * contains name, type (NUMBER|LOGIC|TEXT), value, unit, symbol
 * @see br.uff.labtempo.osiris.to.common.data.ValueTo
 * @see ValueType
 * @see SensorGenerator
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Data
public class SensorValueGenerator {

    private final int VALUE_RANGE = 99999;
    private List<SensorValue> sensorValues;

    public SensorValueGenerator(){
        this.sensorValues = new ArrayList<>();
        this.sensorValues.add(new SensorValue("temperature", ValueType.NUMBER, randomLong(VALUE_RANGE),"celsius", "ºC"));
        this.sensorValues.add(new SensorValue("temperature", ValueType.NUMBER, randomLong(VALUE_RANGE), "fahrenheit", "ºF"));
        this.sensorValues.add(new SensorValue("temperature", ValueType.NUMBER, randomLong(VALUE_RANGE), "kelvin", "K"));
        this.sensorValues.add(new SensorValue("luminosity", ValueType.NUMBER, randomLong(VALUE_RANGE), "candela", "C"));
        this.sensorValues.add(new SensorValue("pressure", ValueType.NUMBER, randomLong(VALUE_RANGE), "pascal", "P"));
        this.sensorValues.add(new SensorValue("boolean", ValueType.LOGIC, randomLong(VALUE_RANGE), "boolean", "(boolean)"));
        this.sensorValues.add(new SensorValue("length", ValueType.NUMBER, randomLong(VALUE_RANGE), "meter", "m"));
        this.sensorValues.add(new SensorValue("mass", ValueType.NUMBER, randomLong(VALUE_RANGE), "kilogram", "kg"));
        this.sensorValues.add(new SensorValue("time", ValueType.NUMBER, randomLong(VALUE_RANGE), "second", "s"));
        this.sensorValues.add(new SensorValue("electric current", ValueType.NUMBER, randomLong(VALUE_RANGE), "ampere", "A"));
        this.sensorValues.add(new SensorValue("frequency", ValueType.NUMBER, randomLong(VALUE_RANGE), "hertz", "Hz"));
        this.sensorValues.add(new SensorValue("energy", ValueType.NUMBER, randomLong(VALUE_RANGE), "energy", "J"));
        this.sensorValues.add(new SensorValue("power", ValueType.NUMBER, randomLong(VALUE_RANGE), "watt", "W"));
        this.sensorValues.add(new SensorValue("voltage", ValueType.NUMBER, randomLong(VALUE_RANGE),"volt", "V"));
    }

    /**
     * Generate a random Set of Sensor Values
     * @see SensorValue
     * @see ValueType
     * @see br.uff.labtempo.osiris.to.common.data.ValueTo
     * @return Set of SensorValue
     */
    public Set<SensorValue> getSensorValueSet() {
        Set<SensorValue> sensorValues = new HashSet<>();
        for(int i = 0; i < (int) (Math.random() * randomSizeRange() + 1); i++) {
            sensorValues.add(this.sensorValues.get(randomSizeRange()));
        }
        return sensorValues;
    }

    /**
     * Generate a random int value from 0 to this.sensorValues.size() range
     * @return int value within the specified range
     */
    private int randomSizeRange() {
        return (int) (Math.random() * (this.sensorValues.size()));
    }

    /**
     * Generate a random long value from 0 to maxRange range
     * @param maxRange
     * @return long value within specified the specified range
     */
    private long randomLong(int maxRange) {
        return (long) (Math.random() * (maxRange + 1));
    }
}
