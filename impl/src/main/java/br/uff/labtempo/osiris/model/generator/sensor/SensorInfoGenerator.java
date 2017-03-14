package br.uff.labtempo.osiris.model.generator.sensor;


import br.uff.labtempo.osiris.model.domain.sensor.SensorInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    public SensorInfo generate() {
        return this.sensorInfos.get((int)(Math.random() * this.sensorInfos.size()));
    }
}

