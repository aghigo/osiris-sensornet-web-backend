package br.uff.labtempo.osiris.generator.sensor;

import br.uff.labtempo.osiris.model.domain.sensor.SensorInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Complementary class of SensorGenerator to generate random Sensor Info
 * An Info contains name and description
 * @see br.uff.labtempo.osiris.to.common.data.InfoTo
 * @see SensorGenerator
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Data
public class SensorInfoGenerator {

    private List<SensorInfo> sensorInfos;

    public SensorInfoGenerator() {
        this.sensorInfos = new ArrayList<>();
        this.sensorInfos.add(SensorInfo.builder().infoName("sendCount").infoDescription("quantity of sent values").build());
        this.sensorInfos.add(SensorInfo.builder().infoName("parent").infoDescription("parent sensor").build());
        this.sensorInfos.add(SensorInfo.builder().infoName("metric").infoDescription("environment climate").build());
        this.sensorInfos.add(SensorInfo.builder().infoName("motelMode").infoDescription("automatic").build());
    }

    /**
     * Generate a random Set of Sensor Infos
     * Values are previous populated on constructor with fixed values
     * @return Set of Sensor Infos
     */
    public Set<SensorInfo> getSensorInfoSet() {
        Set<SensorInfo> sensorInfos = new HashSet<>();
        for(int i = 0; i < randomSizedRange() + 1; i++){
            sensorInfos.add(this.sensorInfos.get(randomSizedRange()));
        }
        return sensorInfos;
    }

    /**
     * Generate a random int value from 0 to this.sensorInfos.size()
     * Used randomize the Info Set size
     * @return int random value within the range
     */
    private int randomSizedRange() {
        return (int) (Math.random() * this.sensorInfos.size());
    }
}

