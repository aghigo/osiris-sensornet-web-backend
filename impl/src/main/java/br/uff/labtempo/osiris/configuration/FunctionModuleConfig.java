package br.uff.labtempo.osiris.configuration;

import lombok.*;

/**
 * Class to map a specific FunctionFactory module configuration
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class FunctionModuleConfig {
    private String functionName;
    private String moduleName;
    private String moduleUri;
    private String interfaceUri;
    private String ip;
    private int port;
    private String username;
    private String password;
}
