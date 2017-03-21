package br.uff.labtempo.osiris.model.generator.sensor;

import br.uff.labtempo.osiris.model.domain.sensor.SensorInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Set<SensorInfo> generate() {
        Set<SensorInfo> sensorInfos = new HashSet<>();
        for(int i = 0; i < randomSizedRange() + 1; i++){
            sensorInfos.add(this.sensorInfos.get(randomSizedRange()));
        }
        return sensorInfos;
    }

    public int randomSizedRange() {
        return (int) (Math.random() * this.sensorInfos.size());
    }
}

