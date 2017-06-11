package br.uff.labtempo.osiris.configuration;

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
 * FunctionFactory module configuration data with all FunctionFactory modules
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
public class AvailableFunctionListConfig {
    @Value("${function.name:%1$s.function}")
    private String moduleName;

    @Value("${function.uri:omcp://%1$s.function/}")
    private String functionUri;

    @Value("${function.uri.interface:omcp://%1$s.function/interface/}")
    private String functionInterfaceUri;

    @Value("#{'${function.names:average,sum,min,max}'.split(',')}")
    private List<String> functionNames;

    private List<FunctionModuleConfig> functionModuleConfigList;

    /**
     * Get a FunctionFactory module configuration based on the function name
     * @param functionName
     * @see FunctionModuleConfig
     * @return FunctionModuleConfig
     */
    public FunctionModuleConfig getFunctionModuleConfig(String functionName) {
        for(FunctionModuleConfig functionModuleConfig : this.functionModuleConfigList) {
            if(functionModuleConfig.getFunctionName().equals(functionName)) {
                return functionModuleConfig;
            }
        }
        throw new RuntimeException(String.format("FunctionFactory %s configuration could not be found in default application.properties file.", functionName));
    }

    /**
     * Get a list of all available FunctionConfigModules
     * @see FunctionModuleConfig
     * @return List of FunctionModuleConfig
     */
    public List<FunctionModuleConfig> getFunctionModuleConfigList() {
        return this.functionModuleConfigList;
    }

    /**
     * Get all FunctionFactory modules configurations in default application.properties configuration file
     * @throws IOException
     */
    @PostConstruct
    public void getFunctionConfig() throws IOException {
        this.functionModuleConfigList = new ArrayList<>();
        for(String functionName : this.functionNames) {
            Properties properties = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream("application.properties");
            properties.load(stream);
            if(properties.getProperty("function." + functionName + ".ip") == null) {
                continue;
            }
            FunctionModuleConfig functionModuleConfig = FunctionModuleConfig.builder()
                    .functionName(functionName)
                    .moduleName(String.format(this.getModuleName(), functionName))
                    .moduleUri(String.format(this.getFunctionUri(), functionName))
                    .interfaceUri(String.format(this.getFunctionInterfaceUri(), functionName))
                    .ip(properties.getProperty("function." + functionName + ".ip"))
                    .port(new Integer(properties.getProperty("function." + functionName + ".port")))
                    .username(properties.getProperty("function." + functionName + ".username"))
                    .password(properties.getProperty("function." + functionName + ".password"))
                    .build();
            this.functionModuleConfigList.add(functionModuleConfig);
        }
    }
}
