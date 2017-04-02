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
    @Value("${virtualsensornet.ip:127.0.0.1}")
    private String ip;

    @Value("${virtualsensornet.port:8091}")
    private int port;

    @Value("${virtualsensornet.username:guest}")
    private String username;

    @Value("${virtualsensornet.password:guest}")
    private String password;

    @Value("${virtualsensornet.uri=omcp://virtualsensornet.osiris/}")
    private String virtualSensorNetUri;

    @Value("${virtualsensornet.uri.datatypes:omcp://virtualsensornet.osiris/datatype}")
    private String dataTypesUri;

    @Value("${virtualsensornet.uri.datatypeId:omcp://virtualsensornet.osiris/datatype/%1$s/}")
    private String dataTypeIdUri;
}
