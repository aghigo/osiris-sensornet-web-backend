package br.uff.labtempo.osiris.model.domain.sensor;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Class that represents a SensorNet Sensor model
 * @see br.uff.labtempo.osiris.to.collector.SensorCoTo
 * @see br.uff.labtempo.osiris.to.sensornet.SensorSnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {

    @NotNull @NotEmpty
    private String id;

    @Min(1)
    private long captureDateInMillis;

    @Min(1)
    private int captureDateInNano;

    @Min(1)
    private long acquisitionDateInMillis;

    @NotNull @NotEmpty
    private Map<String, String> info;

    @NotNull @NotEmpty
    private List<SensorValue> values;

    @NotNull @NotEmpty
    private Map<String, Integer> consumables;

    @NotNull @NotEmpty
    private List<SensorConsumableRule> rules;
}
