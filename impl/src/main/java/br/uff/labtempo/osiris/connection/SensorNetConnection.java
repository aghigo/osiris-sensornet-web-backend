package br.uff.labtempo.osiris.connection;


import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SensorNetConnection {
    @Autowired
    private SensorNetConfig sensorNetConfig;

    public OmcpClient getConnection() {
        return new RabbitClient(this.sensorNetConfig.getIp(), this.sensorNetConfig.getUsername(), this.sensorNetConfig.getPassword());
    }
}
