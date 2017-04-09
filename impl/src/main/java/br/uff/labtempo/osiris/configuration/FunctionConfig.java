package br.uff.labtempo.osiris.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@PropertySource(value = "classpath:application.properties")
public class FunctionConfig {
    @Value("${function.uri:omcp://%1$s.function.osiris}")
    private String functionUri;

    @Value("${function.uri.interface}")
    private String functionInterfaceUri;

    @Value("${function.names}")
    private List<String> functionNames;
}
