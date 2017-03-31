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

    private State state;

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
