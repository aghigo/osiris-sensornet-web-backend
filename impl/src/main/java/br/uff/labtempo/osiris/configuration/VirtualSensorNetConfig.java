package br.uff.labtempo.osiris.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@PropertySource(value = "classpath:application.properties")
public class VirtualSensorNetConfig {
    @Value("${virtualsensornet.messgegroup.collector:omcp://collector.messagegroup/}")
    private String collectorMessageGroupUri;

    @Value("${virtualsensornet.uri:omcp://sensornet.osiris/}")
    private String moduleUri;

    @Value("${virtualsensornet.uri.networks:omcp://sensornet.osiris/}")
    private String networksUri;

    @Value("${virtualsensornet.uri.networkId:omcp://sensornet.osiris/%1$s/}")
    private String networkIdUri;

    @Value("${virtualsensornet.uri.networkId.collectors:omcp://sensornet.osiris/%1$s/collector/}")
    private String networkIdCollectorsUri;

    @Value("${virtualsensornet.uri.networkId.sensors:omcp://sensornet.osiris/%1$s/sensor/}")
    private String networkIdSensorsUri;

    @Value("${virtualsensornet.uri.networkId.collectorId:omcp://sensornet.osiris/%1$s/collector/%2$s/}")
    private String networkIdCollectorIdUri;

    @Value("${virtualsensornet.uri.networkId.collectorId.sensors:omcp://sensornet.osiris/%1$s/collector/%2$s/sensor/}")
    private String networkIdCollectorIdSensorsUri;

    @Value("${virtualsensornet.uri.networkId.collectorId.sensorId:omcp://sensornet.osiris/%1$s/collector/%2$s/sensor/%3$s/}")
    private String networkIdCollectorIdSensorIdUri;
}
