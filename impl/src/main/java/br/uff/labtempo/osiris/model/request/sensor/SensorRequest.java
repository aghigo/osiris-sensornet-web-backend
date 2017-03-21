package br.uff.labtempo.osiris.model.request.sensor;

import br.uff.labtempo.osiris.model.domain.sensor.SensorConsumableRule;
import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SensorRequest {
    private String id;
    private long captureTimestampInMillis;
    private int capturePrecisionInNano;
    private long acquisitionTimestampInMillis;
    private Map<String, String> info;
    private List<SensorValue> values;
    private Map<String, Integer> consumables;
    private List<SensorConsumableRule> rules;
}
