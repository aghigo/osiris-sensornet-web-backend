package br.uff.labtempo.osiris.model.request.sensor;

import br.uff.labtempo.osiris.model.domain.SensorConsumableRule;
import br.uff.labtempo.osiris.model.domain.SensorValue;
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
    private long captureDateInMillis;
    private int captureDateInNano;
    private long acquisitionDateInMillis;
    private Map<String, String> info;
    private List<SensorValue> values;
    private Map<String, Integer> consumable;
    private List<SensorConsumableRule> consumableRule;
}
