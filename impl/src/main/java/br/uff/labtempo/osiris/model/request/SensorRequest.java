package br.uff.labtempo.osiris.model.request;

import br.uff.labtempo.osiris.model.domain.sensor.SensorConsumableRule;
import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import lombok.*;

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
    private String id;
    private long captureDateInMillis;
    private int captureDateInNano;
    private long acquisitionDateInMillis;
    private Map<String, String> info;
    private List<SensorValue> values;
    private Map<String, Integer> consumables;
    private List<SensorConsumableRule> rules;
}
