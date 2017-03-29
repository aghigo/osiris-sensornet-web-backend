package br.uff.labtempo.osiris.model.response;

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
public class SensorResponse {
    @Min(1)
    private long lastModified;

    @NotNull @NotEmpty
    private String networkId;

    @NotNull @NotEmpty
    private String collectorId;

    @Min(1)
    private long storageTimestampInMillis;

    @Min(1)
    private long captureTimestampInMillis;

    @Min(1)
    private long acquisitionTimestampInMillis;

    @Min(1)
    private long capturePrecisionInNano;

    @NotNull @NotEmpty
    private Map<String, Integer> consumables;

    @NotNull @NotEmpty
    private List<SensorValue> values;

    @NotNull @NotEmpty
    private String id;

    @NotNull
    private State state;

    @NotNull @NotEmpty
    private Map<String, String> info;
}
