package br.uff.labtempo.osiris.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * VirtualSensorNet module configuration data
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
public class VirtualSensorNetConfig {
    @Value("${virtualsensornet.moduleName:virtualsensornet}")
    private String moduleName;

    @Value("${virtualsensornet.uri:omcp://virtualsensornet.osiris/}")
    private String moduleUri;

    @Value("${virtualsensornet.uri.datatypes:omcp://virtualsensornet.osiris/datatype}")
    private String dataTypesUri;

    @Value("${virtualsensornet.uri.datatypeId:omcp://virtualsensornet.osiris/datatype/%1$s/}")
    private String dataTypeIdUri;

    @Value("${virtualsensornet.uri.links:omcp://virtualsensornet.osiris/vsensor/}")
    private String linksUri;

    @Value("${virtualsensornet.uri.linkId:omcp://virtualsensornet.osiris/vsensor/%1$s/}")
    private String linkIdUri;

    @Value("${virtualsensornet.uri.composites:omcp://virtualsensornet.osiris/composite}")
    private String compositesUri;

    @Value("${virtualsensornet.uri.compositeId:omcp://virtualsensornet.osiris/composite/%1$s/}")
    private String compositeIdUri;

    @Value("${virtualsensornet.uri.blendings:omcp://virtualsensornet.osiris/blending}")
    private String blendingsUri;

    @Value("${virtualsensornet.uri.blendingId:omcp://virtualsensornet.osiris/blending/%1$s/}")
    private String blendingIdUri;

    @Value("${virtualsensornet.uri.virtualSensor:omcp://virtualsensornet.osiris/vsensor/}")
    private String virtualSensorUri;

    @Value("${virtualsensornet.uri.virtualSensorId:omcp://virtualsensornet.osiris/vsensor/%1$s/}")
    private String virtualSensorIdUri;

    @Value("${virtualsensornet.uri.function:omcp://virtualsensornet.osiris/function}")
    private String functionUri;

    @Value("${virtualsensornet.uri.functionName:omcp://virtualsensornet.osiris/function/%1$s/}")
    private String functionNameUri;
}
