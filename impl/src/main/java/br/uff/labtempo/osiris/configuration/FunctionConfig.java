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
public class FunctionConfig {
    @Value("${function.uri.average:omcp://average.function.osiris}")
    private String averageFunctionUri;

    @Value("${function.uri.sum:omcp://sum.function.osiris}")
    private String sumFunctionUri;

    @Value("${function.uri.min:omcp://min.function.osiris}")
    private String minFunctionUri;

    @Value("${function.uri.max:omcp://max.function.osiris}")
    private String maxFunctionUri;
}
