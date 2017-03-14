package br.uff.labtempo.osiris.connection;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorNetConnection {
    private OmcpClient omcpClient;
    private SensorNetConfig sensorNetConfig;

    public SensorNetConnection(){
        this.omcpClient = new RabbitClient(this.sensorNetConfig.getHost(), this.sensorNetConfig.getUsername(), this.sensorNetConfig.getPassword());
    }



}
