package br.uff.labtempo.osiris.configuration;

import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * FunctionModuleFactory module configuration data with all FunctionModuleFactory modules
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
public class FunctionModuleConfig {
    private final FunctionOperation defaultOperation = FunctionOperation.SYNCHRONOUS;
    private final ValueType defaultParamType = ValueType.NUMBER;
    private final String fullNameTemplate = "%s.function";

    @Value("${function.uri:omcp://%1$s.function/}")
    private String omcpUriTemplate;

    @Value("${function.uri.interface:omcp://%1$s.function/interface/}")
    private String omcpInterfaceUriTemplate;

    private final String restDataUriTemplate = "/function/data/%s/";
    private final String restInterfaceUriTemplate = "/function/interface/%s/";
}
