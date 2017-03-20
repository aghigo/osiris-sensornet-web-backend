package br.uff.labtempo.osiris.model.request.sensor;

import br.uff.labtempo.osiris.model.domain.Sensor;
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
    private List<Sensor> sensors;
}
