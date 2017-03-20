package br.uff.labtempo.osiris.model.domain.sensor;

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
public class Sensor {
    private String id;
    private long captureDateInMillis;
    private int captureDateInNano;
    private long acquisitionDateInMillis;
    private Map<String, String> info;
    private List<SensorValue> values;
    private Map<String, Integer> consumables;
    private List<SensorConsumableRule> rules;
}
