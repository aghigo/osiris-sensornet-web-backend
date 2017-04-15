package br.uff.labtempo.osiris.model;

import lombok.*;

/**
 * Class that represent a Osiris web application dependency
 * e.g. (SensorNet module, VirtualSensorNet module, Function Module, RabbitMQ queue, Application database, etc)
 * @see br.uff.labtempo.osiris.connection.SensorNetConnection
 * @see br.uff.labtempo.osiris.connection.VirtualSensorNetConnection
 * @see br.uff.labtempo.osiris.connection.FunctionConnection
 * @see br.uff.labtempo.osiris.connection.RabbitMQConnection
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class HealthDependency {
    private String name;
    private String ip;
    private int port;
    private String uri;
    private String status;
}
