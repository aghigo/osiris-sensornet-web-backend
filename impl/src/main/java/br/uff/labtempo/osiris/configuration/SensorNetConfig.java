package br.uff.labtempo.osiris.configuration;

import lombok.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@PropertySource(value = "classpath:application.properties")
public class SensorNetConfig {
    @org.springframework.beans.factory.annotation.Value("${osiris.module.sensornet.uri}")
    private String uri;

    @org.springframework.beans.factory.annotation.Value("${osiris.module.sensornet.ip}")
    private String ip;

    @org.springframework.beans.factory.annotation.Value("${osiris.module.sensornet.port}")
    private int port;

    @org.springframework.beans.factory.annotation.Value("${osiris.module.sensornet.username}")
    private String username;

    @org.springframework.beans.factory.annotation.Value("${osiris.module.sensornet.password}")
    private String password;
}
