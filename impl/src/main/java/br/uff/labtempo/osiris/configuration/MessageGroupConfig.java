package br.uff.labtempo.osiris.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class for mapping osiris messagegroups configurations
 * in application.properties file.
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MessageGroupConfig {
    @Value("#{'${osiris.messagegroup.names:collector,notification,update}'.split(',')}")
    private List<String> messageGroupNameList;

    private List<OsirisMessageGroupConfig> osirisMessageGroupConfigList;

    @PostConstruct
    public void readConfigurations() throws IOException {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("application.properties");
        properties.load(stream);
        this.osirisMessageGroupConfigList = new ArrayList<>();
        for(String messageGroupName : this.messageGroupNameList) {
            if(properties.getProperty("osiris.messagegroup." + messageGroupName + ".name") != null) {
                OsirisMessageGroupConfig osirisMessageGroupConfig = OsirisMessageGroupConfig.builder()
                        .ip(properties.getProperty("osiris.messagegroup." + messageGroupName + ".ip"))
                        .name(properties.getProperty("osiris.messagegroup." + messageGroupName + ".name"))
                        .password(properties.getProperty("osiris.messagegroup." + messageGroupName + ".password"))
                        .port(new Integer(properties.getProperty("osiris.messagegroup." + messageGroupName + ".port")))
                        .username(properties.getProperty("osiris.messagegroup." + messageGroupName + ".username"))
                        .uri(properties.getProperty("osiris.messagegroup." + messageGroupName + ".uri"))
                        .build();
                this.osirisMessageGroupConfigList.add(osirisMessageGroupConfig);
            }
        }
    }
}
