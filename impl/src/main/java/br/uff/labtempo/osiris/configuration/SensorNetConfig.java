package br.uff.labtempo.osiris.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ConfigurationProperties(value = "classpath:application.properties", prefix = "osiris.module.sensornet")
public class SensorNetConfig {
    private String omcpUri;
    private String host;
    private int port;
    private String username;
    private String password;
}
