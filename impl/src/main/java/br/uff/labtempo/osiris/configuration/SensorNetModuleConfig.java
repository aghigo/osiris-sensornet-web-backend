package br.uff.labtempo.osiris.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * SensorNet module configuration data
 * values are retrieved from the application.properties configuration file
 * module name, mapped component URIs
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@PropertySource(value = "classpath:application.properties")
public class SensorNetModuleConfig {
    @Value("${sensornet.moduleName:sensornet}")
    private String moduleName;

    @Value("${sensornet.uri:omcp://sensornet.osiris/}")
    private String moduleUri;

    @Value("${sensornet.messgegroup.collector:omcp://collector.messagegroup/}")
    private String collectorMessageGroupUri;

    @Value("${sensornet.uri.networks:omcp://sensornet.osiris/}")
    private String networksUri;

    @Value("${sensornet.uri.networkId:omcp://sensornet.osiris/%1$s/}")
    private String networkIdUri;

    @Value("${sensornet.uri.networkId.collectors:omcp://sensornet.osiris/%1$s/collector/}")
    private String networkIdCollectorsUri;

    @Value("${sensornet.uri.networkId.sensors:omcp://sensornet.osiris/%1$s/sensor/}")
    private String networkIdSensorsUri;

    @Value("${sensornet.uri.networkId.collectorId:omcp://sensornet.osiris/%1$s/collector/%2$s/}")
    private String networkIdCollectorIdUri;

    @Value("${sensornet.uri.networkId.collectorId.sensors:omcp://sensornet.osiris/%1$s/collector/%2$s/sensor/}")
    private String networkIdCollectorIdSensorsUri;

    @Value("${sensornet.uri.networkId.collectorId.sensorId:omcp://sensornet.osiris/%1$s/collector/%2$s/sensor/%3$s/}")
    private String networkIdCollectorIdSensorIdUri;

    @Value("${sensornet.location.created.sample:/networks/%1$s/collectors/%2$s/sensors/%3$s/}")
    private String sampleCreatedLocation;
}
