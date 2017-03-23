package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component(value = "sampleOmcpDao")
public class SampleOmcpDao implements SampleRepository {

    @Autowired
    private SensorNetConfig sensorNetConfig;

    @Override
    public void notify(SampleCoTo sample) {
        String uri = sensorNetConfig.getCollectorMessageGroupUri();
        String ip = sensorNetConfig.getIp();
        String username = sensorNetConfig.getUsername();
        String password = sensorNetConfig.getPassword();
        OmcpClient omcpClient = new RabbitClient(ip, username, password);
        omcpClient.doNofity(uri, sample);
    }
}
