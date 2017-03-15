package br.uff.labtempo.osiris.model.domain.sensor;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class SensorValue {
    private String name;
    private long value;
    private String unit;
    private String symbol;
}
