package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.model.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import org.springframework.stereotype.Service;

@Service
public class SensorService {
    public SensorCoTo getAll(){
        SensorGenerator sensorGenerator = new SensorGenerator();
        return sensorGenerator.generate();
    }

    public void post(CollectorCoTo collectorCoTo) {

    }
}
