package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.model.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

    private SensorGenerator sensorGenerator;

    @Autowired
    public SensorService(SensorGenerator sensorGenerator) {
        this.sensorGenerator = sensorGenerator;
    }

    public SensorCoTo getRandom() {
        return this.sensorGenerator.generate();
    }

}
