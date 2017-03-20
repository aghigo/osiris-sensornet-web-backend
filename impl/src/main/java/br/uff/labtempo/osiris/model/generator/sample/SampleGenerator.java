package br.uff.labtempo.osiris.model.generator.sample;

import br.uff.labtempo.osiris.model.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.model.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.model.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleGenerator {

    private NetworkGenerator networkGenerator;
    private CollectorGenerator collectorGenerator;
    private SensorGenerator sensorGenerator;

    @Autowired
    public SampleGenerator(NetworkGenerator networkGenerator, CollectorGenerator collectorGenerator, SensorGenerator sensorGenerator) {
        this.networkGenerator = networkGenerator;
        this.collectorGenerator = collectorGenerator;
        this.sensorGenerator = sensorGenerator;
    }

    public SampleCoTo generate() {
        SampleCoTo sampleCoTo = new SampleCoTo(this.networkGenerator.generate(), this.collectorGenerator.generate(), this.sensorGenerator.generate());
        return sampleCoTo;
    }

    public SampleCoTo generate(NetworkCoTo networkCoTo, CollectorCoTo collectorCoTo, SensorCoTo sensorCoTo) {
        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
        return sampleCoTo;
    }
}
