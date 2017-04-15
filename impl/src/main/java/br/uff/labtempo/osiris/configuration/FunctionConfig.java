package br.uff.labtempo.osiris.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Function module configuration data
 * values are retrieved from the application.properties configuration file
 * module name, mapped component URIs, function names
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
public class FunctionConfig {
    @Value("${function.name:Function}")
    private String moduleName;

    @Value("${function.uri:omcp://%1$s.function.osiris}")
    private String functionUri;

    @Value("${function.uri.interface:omcp://%1$s.function.osiris/interface/}")
    private String functionInterfaceUri;

    @Value("#{'${function.names:average,sum,min,max}'.split(',')}")
    private List<String> functionNames;
}
