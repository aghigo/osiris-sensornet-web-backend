package br.uff.labtempo.osiris.generator.sample;

import br.uff.labtempo.osiris.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.generator.sensor.SensorGenerator;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class responsible to generate random Sample mock objects for SensorNet module
 * A Sample is an object that encapsulates and relates Network + Collector + Sensor
 * @see SampleCoTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
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

    /**
     * Generates a random Sample mock object
     * @see SampleCoTo
     * @return SampleCoTo
     */
    public SampleCoTo getSampleCoTo() {
        SampleCoTo sampleCoTo = new SampleCoTo(this.networkGenerator.getNetworkCoto(), this.collectorGenerator.getCollectorCoTo(), this.sensorGenerator.getSensorCoTo());
        return sampleCoTo;
    }

    /**
     * Creates an Sample object based on network, collector and sensor objects
     * @param networkCoTo
     * @param collectorCoTo
     * @param sensorCoTo
     * @return SampleCoTo
     */
    public SampleCoTo getSampleCoTo(NetworkCoTo networkCoTo, CollectorCoTo collectorCoTo, SensorCoTo sensorCoTo) {
        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
        return sampleCoTo;
    }
}
