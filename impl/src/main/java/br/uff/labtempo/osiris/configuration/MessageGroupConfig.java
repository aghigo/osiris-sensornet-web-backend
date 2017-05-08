package br.uff.labtempo.osiris.configuration;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * Model class to map specific MessageGroup configuration
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
    private String name;
    private String uri;
    private String ip;
    private int port;
    private String username;
    private String password;
}