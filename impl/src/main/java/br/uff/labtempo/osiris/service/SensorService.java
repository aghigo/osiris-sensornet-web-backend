package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.model.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    private SensorRepository sensorRepository;
    private SensorGenerator sensorGenerator;

    @Autowired
    public SensorService(SensorRepository sensorRepository, SensorGenerator sensorGenerator) {
        this.sensorRepository = sensorRepository;
        this.sensorGenerator = sensorGenerator;
    }

    public SensorCoTo getRandom() {
        return this.sensorGenerator.generate();
    }

    public SensorCoTo getByCollectorIdAndNetworkId(String networkId, String collectorId, String sensorId) {
        return this.sensorRepository.getByCollectorIdAndNetworkId(networkId, collectorId, sensorId);
    }

    public List<SensorCoTo> getAllByCollectorIdAndNetworkId(String networkId, String collectorId) {
        return this.sensorRepository.getAllByCollectorIdAndNetworkId(networkId, collectorId);
    }
}
