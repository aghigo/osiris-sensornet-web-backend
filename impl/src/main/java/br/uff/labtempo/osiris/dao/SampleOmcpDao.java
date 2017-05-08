package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.osiris.configuration.SensorNetModuleConfig;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DAO class for Sample CRUD on SensorNet module
 * A Sample object encapsulates and relates Network + Collector + Sensor
 * @see SampleCoTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component(value = "sampleOmcpDao")
public class SampleOmcpDao implements SampleRepository {

    @Autowired
    private SensorNetModuleConfig sensorNetModuleConfig;

    @Autowired
    private SensorNetConnection connection;

    /**
     * Creates a new Sample on SensorNet module
     * @param sample
     */
    @Override
    public void save(SampleCoTo sample) {
        String uri = sensorNetModuleConfig.getCollectorMessageGroupUri();
        OmcpClient omcpClient = this.connection.getConnection();
        omcpClient.doNofity(uri, sample);
    }
}
