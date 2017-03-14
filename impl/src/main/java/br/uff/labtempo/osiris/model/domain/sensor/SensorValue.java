package br.uff.labtempo.osiris.model.domain.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
public class SensorValue {
    private String name;
    private long value;
    private String unit;
    private String symbol;
}
