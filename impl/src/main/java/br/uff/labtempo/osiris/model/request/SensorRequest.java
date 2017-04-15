package br.uff.labtempo.osiris.model.request;

import br.uff.labtempo.osiris.model.domain.sensor.SensorConsumableRule;
import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import br.uff.labtempo.osiris.to.common.definitions.State;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Sensor request data required to create/update Sensors on SensorNet module
 * @see br.uff.labtempo.osiris.to.collector.SensorCoTo
 * @see br.uff.labtempo.osiris.to.sensornet.SensorSnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SensorRequest {
    @NotNull @NotEmpty
    private String id;

    /**
     * NEW, INACTIVE, UPDATED, REACTIVATED, MALFUNCTION
     * @see State
     */
    private State state;

    @Min(1)
    private long captureDateInMillis;

    @Min(1)
    private int captureDateInNano;

    @Min(1)
    private long acquisitionDateInMillis;

    /**
     * Map of infos containing name (Key) and description (Value)
     * @see br.uff.labtempo.osiris.to.common.data.InfoTo
     */
    @NotNull @NotEmpty
    private Map<String, String> info;

    /**
     * Sensor Value containing name, type, unit, symbol
     * @see SensorValue
     * @see br.uff.labtempo.osiris.to.common.data.ValueTo
     */
    @NotNull @NotEmpty
    private List<SensorValue> values;

    /**
     * @see br.uff.labtempo.osiris.to.common.data.ConsumableTo
     */
    @NotNull @NotEmpty
    private Map<String, Integer> consumables;

    /**
     * @see br.uff.labtempo.osiris.to.common.data.ConsumableRuleTo
     */
    @NotNull @NotEmpty
    private List<SensorConsumableRule> rules;
}
